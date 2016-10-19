package org.idou.entity;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/17.
 */
public class SeckillDetail {
    private long seckillId;
    private long userPhone;
    private short state;
    private Date createTime;

    private Seckill Seckill;
    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return Seckill;
    }

    public void setSeckill(Seckill seckill) {
        Seckill = seckill;
    }

    @Override
    public String toString() {
        return "SeckillDetail{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
