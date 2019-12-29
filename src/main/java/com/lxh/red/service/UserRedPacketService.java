package com.lxh.red.service;

import com.lxh.red.pojo.UserRedPacket;

public interface UserRedPacketService {
    /*
     * @Author 辉
     * @Description //TODO 添加每个用户抢的红包
     * @Date 22:45 2019/12/28
     * @Param [userRedPacket]
     * [抢红包信息]
     * @return int 影响记录条数
     **/
    public int grapRedPacket(Long redPacketId, Integer userId);

    public int getRedPacketByVersion(Long id, Integer version);
}
