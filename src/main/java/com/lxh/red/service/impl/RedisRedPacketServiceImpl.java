package com.lxh.red.service.impl;

import com.lxh.red.mapper.RedPacketMapper;
import com.lxh.red.mapper.UserRedPacketMapper;
import com.lxh.red.pojo.UserRedPacket;
import com.lxh.red.service.RedisRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * @PackageName: com.lxh.red.service.impl
 * @ClassName: RedisRedPacketServiceImpl
 * @Description:
 * @author: 辉
 * @date: 2020/1/16 1:30
 * */
@Service
public class RedisRedPacketServiceImpl implements RedisRedPacketService {

    private static final String PREFIX = "red_packet_list_";
    //每次取出1000条，避免一次取出消耗太多
    private static final int TIME_SIZE = 1000;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DataSource dataSource;//数据源

    @Autowired
    RedPacketMapper redPacketMapper;
    @Autowired
    UserRedPacketMapper userRedPacketMapper;

    @Override
    @Async//开启新线程运行
    public void saveByRedis(Long redPacketId, Double unitAmount) {
        System.out.println("开始保存数据");
        Long start = System.currentTimeMillis();

        //获取列表操作对象
        BoundListOperations ops = redisTemplate.boundListOps(PREFIX + redPacketId);
        Long size = ops.size();
        //获取操作次数
        Long times = size%TIME_SIZE == 0 ? size / TIME_SIZE : size / TIME_SIZE + 1;

        List<UserRedPacket> userRedPackets = new ArrayList<>(TIME_SIZE);

        for (int i = 0; i < times; i++) {
            //获取多个TIME_SIZE个抢红包信息
            List userIdList = null;
            if (i == 0) {
                userIdList = ops.range(i*TIME_SIZE, (i+1)*TIME_SIZE);
            } else {
                userIdList = ops.range(i*TIME_SIZE+1, (i+1)*TIME_SIZE);
            }

            userRedPackets.clear();

            //保存红包信息
            for (int j = 0; j < userIdList.size(); j++) {
                String args = userIdList.get(j).toString();
                String[] arr = args.split("-");
                String userIdStr = arr[0];
                String timeStr = arr[1];

                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setAmount(new BigDecimal(unitAmount));
                userRedPacket.setUserId(Long.parseLong(userIdStr));
                userRedPacket.setGrabTime(new Timestamp(Long.parseLong(timeStr)));
                userRedPacket.setNote("已抢红包10元");
                userRedPacket.setRedPackId(redPacketId);
                userRedPackets.add(userRedPacket);

            }
            //插入抢红包信息
            saveData(userRedPackets);
        }
        //删除Redis列表
        redisTemplate.delete(PREFIX+redPacketId);
        Long end = System.currentTimeMillis();
        System.out.println("保存数据结束，耗时" + (end - start) + "毫秒");
    }

    public int saveData(List<UserRedPacket> list){
        int count = 0;
        for (UserRedPacket user : list) {
            userRedPacketMapper.grapRedPacket(user);
            redPacketMapper.decreaseRedPacket(user.getRedPackId());
            count++;
        }
        return count;
    }
}
