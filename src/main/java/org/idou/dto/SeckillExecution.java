package org.idou.dto;

import org.idou.entity.SeckillDetail;
import org.idou.enums.SeckillStatuEnum;

/**
 * 封装秒杀执行后的结果
 * Created by ZhangSi on 2016/10/18.
 */
public class SeckillExecution {
    private long seckillId;
    //秒杀执行结果状态
    private int state;
    //状态标识
    private String stateInfo;
    //秒杀成功对象
    private SeckillDetail successKilled;

    public SeckillExecution(long seckillId, SeckillStatuEnum statuEnum, SeckillDetail successKilled) {
        this.seckillId = seckillId;
        this.state = statuEnum.getState();
        this.stateInfo = statuEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(long seckillId, SeckillStatuEnum statuEnum) {
        this.seckillId = seckillId;
        this.state = statuEnum.getState();
        this.stateInfo = statuEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SeckillDetail getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SeckillDetail successKilled) {
        this.successKilled = successKilled;
    }
}
