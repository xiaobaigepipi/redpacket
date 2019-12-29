package com.lxh.red.mapper;

import com.lxh.red.pojo.RedPacket;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RedPacketMapper {

    /*
     * @Author 辉
     * @Description //TODO 获取红包信息
     * @Date 22:39 2019/12/28
     * @Param [id]
     *  [红包id]
     * @return com.lxh.red.pojo.RedPacket 红包具体信息
     **/
    public RedPacket getRedPacket(Long id);

    /*
     * @Author 辉
     * @Description //TODO 扣减抢红包数量
     * @Date 22:42 2019/12/28
     * @Param [id]
     * [红包id]
     * @return int 更新记录条数
     **/
    public int decreaseRedPacket(Long id);

    public int decreaseRedPacketForVersion(@Param("id") Long id, @Param("version")Long version);

}
