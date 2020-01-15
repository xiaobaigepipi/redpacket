package com.lxh.red.service;

import org.springframework.stereotype.Service;

/*
 * @PackageName: com.lxh.red.service
 * @ClassName: RedisRedPacketService
 * @Description:
 * @author: 辉
 * @date: 2020/1/16 1:28
 * */
public interface RedisRedPacketService {
    /*
     * @Author 辉
     * @Description //TODO
     * @Date 1:30 2020/1/16
     * @Param [redPacketId, unitAmount]
     * 抢红包编号， 红包金额
     * @return void
     **/
    public void saveByRedis(Long redPacketId, Double unitAmount);
}
