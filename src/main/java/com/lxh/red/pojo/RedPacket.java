package com.lxh.red.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

/*
 * @PackageName: com.lxh.red.pojo
 * @ClassName: RedPacket
 * @Description: 红包总表
 * @author: 辉
 * @date: 2019/12/28 22:30
 * */
public class RedPacket {
    private Long id;
    private Long userId; //发红包用户
    private BigDecimal amount; //红包总金额
    private Timestamp sendDate; //发送日期
    private Integer total; //红包总数
    private BigDecimal unitAmount; //单个红包金额
    private Integer stock; //红包数量剩余
    private Integer version; //版本
    private String note; // 备注

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

    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setSendDate(Timestamp sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
