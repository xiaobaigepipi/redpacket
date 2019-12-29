package com.lxh.red.service.impl;

import com.lxh.red.mapper.RedPacketMapper;
import com.lxh.red.mapper.UserRedPacketMapper;
import com.lxh.red.pojo.RedPacket;
import com.lxh.red.pojo.UserRedPacket;
import com.lxh.red.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}
