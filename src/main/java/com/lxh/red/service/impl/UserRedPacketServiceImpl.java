package com.lxh.red.service.impl;

import com.lxh.red.mapper.RedPacketMapper;
import com.lxh.red.mapper.UserRedPacketMapper;
import com.lxh.red.pojo.RedPacket;
import com.lxh.red.pojo.UserRedPacket;
import com.lxh.red.service.RedisRedPacketService;
import com.lxh.red.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.Date;

/*
 * @PackageName: com.lxh.red.service.impl
 * @ClassName: UserRedPacketServiceImpl
 * @Description:
 * @author: 辉
 * @date: 2019/12/28 23:21
 * */
@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {
    @Autowired
    UserRedPacketMapper userRedPacketMapper = null;
    @Autowired
    RedPacketMapper redPacketMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisRedPacketService redisRedPacketService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grapRedPacket(Long redPacketId, Integer userId) {
        RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);
        //如果红包剩余个数大于0
        if (redPacket.getStock() > 0) {
            //生成一个抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("已抢红包10元");
            userRedPacket.setRedPackId(redPacketId);
            //插入
            userRedPacket.setUserId(Long.parseLong(userId.toString()));
            redPacketMapper.decreaseRedPacket(redPacketId);
            return userRedPacketMapper.grapRedPacket(userRedPacket);
        }
        //失败返回
        return 0;
    }

    @Override
    public int getRedPacketByVersion(Long redPacketId, Integer userId) {
        //乐观锁重入机制，最大限度保证红包不会抢购失败
        for (int i = 0; i < 3; i++) {
            RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);
            //如果红包剩余个数大于0
            if (redPacket.getStock() > 0) {
                //判断是否修改了数据
                int update = redPacketMapper.decreaseRedPacketForVersion(redPacketId, (long) redPacket.getVersion());
                //没修改的话说明刚刚有线程已经修改了刚获取的那一行的数值
                if (update == 0) {
                    return 0;
                }
                //生成一个抢红包信息
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setAmount(redPacket.getUnitAmount());
                userRedPacket.setNote("已抢红包10元");
                userRedPacket.setRedPackId(redPacketId);
                //插入
                userRedPacket.setUserId(Long.parseLong(userId.toString()));
                //redPacketMapper.decreaseRedPacket(redPacketId);
                return userRedPacketMapper.grapRedPacket(userRedPacket);
            }
        }
        //失败返回
        return 0;
    }

    //Lua脚本
    String script = "local listKey = 'red_packet_list_'..KEYS[1] \n"
            + "local redPacket = 'red_packet_'..KEYS[1] \n"
            + "local stock = tonumber(redis.call('hget', redPacket, 'stock')) \n"
            +"if stock <= 0 then return 0 end \n"
            +"stock = stock - 1 \n"
            +"redis.call('hset', redPacket, 'stock', tostring(stock)) \n"
            +"redis.call('rpush', listKey, ARGV[1]) \n"
            +"if stock == 0 then return 2 end \n"
            +"return 1 \n";
    //缓存Lus脚本后，使用该变量保存Redis返回的32为的SHAL编码，使用它去执行缓存的Lua脚本
    String shal = null;

    @Override
    public int getRedPacketByRedis(Long redPacketId, Long userId) {
        //当前抢红包用户和日期信息
        String args = userId + "-" + System.currentTimeMillis();
        Long result = null;

        //获取底层Redis操作对象
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        try {
            //如果脚本没有加载过，那么进行加载，这样就会返回一个shall码
            if (shal == null) {
                shal = jedis.scriptLoad(script);
            }

            //执行脚本，返回结果
            Object res = jedis.evalsha(shal, 1, redPacketId+"", args);
            result = (Long)res;

            //返回2的时候为最后一个红包，此时将红包信息通过异步保存在数据库中
            if (result == 2) {
                String unitAmountStr = jedis.hget("red_packet" + redPacketId, "unit_amount");
                //触发保存数据库
                Double unitAmount = Double.parseDouble(unitAmountStr);
                redisRedPacketService.saveByRedis(redPacketId, unitAmount);
            }
        } finally {
            //确保jedis关闭
            if (jedis != null && jedis.isConnected()) {
                jedis.close();
            }
        }
        return 0;
    }
}
