package com.lxh.red.mapper;

import com.lxh.red.pojo.UserRedPacket;
import org.springframework.stereotype.Repository;

import com.lxh.red.pojo.UserRedPacket;

/*
 * @PackageName: com.lxh.red.mapper
 * @ClassName: UserRedPacketMapper
 * @Description:
 * @author: 辉
 * @date: 2019/12/28 22:44
 * */
@Repository
public interface UserRedPacketMapper {

    /*
     * @Author 辉
     * @Description //TODO 添加每个用户抢的红包
     * @Date 22:45 2019/12/28
     * @Param [userRedPacket]
     * [抢红包信息]
     * @return int 影响记录条数
     **/
    public int grapRedPacket(UserRedPacket userRedPacket);

}
