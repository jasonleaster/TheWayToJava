package org.jasonleaster.seckill.enums;

/**
 * 使用枚举表述常量数据字典
 * Author : jasonleaster
 * Email  : jasonleaster@gmail.com
 * Date   : 2017.01.22
 */
public enum DealStateEnum {
    SUCCESS(1,"DealSuccessful"),
    END(0,"DealClosed"),
    REPAET_ORDER(-1,"RepeatOrder"),
    INNER_ERROR(-2,"SystemError"),
    DATA_REWRITE(-3,"DataRewrite");

    private int state;
    private String stateInfo;

    DealStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static DealStateEnum stateOf(int index){
        for (DealStateEnum state: values()) {
            if (state.getState() == index){
                return state;
            }
        }
        return null;
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
}
