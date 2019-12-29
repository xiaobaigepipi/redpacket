package com.lxh.red.service.impl;

import com.lxh.red.mapper.RedPacketMapper;
import com.lxh.red.pojo.RedPacket;
import com.lxh.red.service.RedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * @PackageName: com.lxh.red.service.impl
 * @ClassName: RedPacketServiceImpl
 * @Description:
 * @author: 辉
 * @date: 2019/12/28 23:16
 * */
@Service
public class RedPacketServiceImpl implements RedPacketService {
    @Autowired
    RedPacketMapper redPacketMapper;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public RedPacket getRedPacket(Long id) {
        return redPacketMapper.getRedPacket(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int decreaseRedPacket(Long id) {
        return redPacketMapper.decreaseRedPacket(id);
    }
}
