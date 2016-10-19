package org.idou.enums;

/**
 * 使用枚举表述常量数据字段
 * Created by ZhangSi on 2016/10/18.
 */
public enum SeckillStatuEnum {
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATA_REWRITE(-3, "数据篡改");

    private int state;
    private String stateInfo;

    SeckillStatuEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStatuEnum stateOf(int index) {
        for (SeckillStatuEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SeckillStatuEnum seckillStatuEnum = SeckillStatuEnum.stateOf(1);
        System.out.println(seckillStatuEnum.getStateInfo());
    }
}
