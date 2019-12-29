package com.lxh.red.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

/*
 * @PackageName: com.lxh.red.pojo
 * @ClassName: UserRedPacketMapper
 * @Description: //抢红包
 * @author: 辉
 * @date: 2019/12/28 22:35
 * */
public class UserRedPacket {
    private Long id;
    private Long userId; //抢红包用户
    private BigDecimal amount; //抢红包金额
    private Timestamp grabTime; //抢红包时间
    private String note; //备注
    private Long redPackId;

    public Long getRedPackId() {
        return redPackId;
    }

    public void setRedPackId(Long redPackId) {
        this.redPackId = redPackId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getGrabTime() {
        return grabTime;
    }

    public void setGrabTime(Timestamp grabTime) {
        this.grabTime = grabTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
