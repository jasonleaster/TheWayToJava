package org.jasonleaster.seckill.dto;

import org.jasonleaster.seckill.enums.DealStateEnum;
import org.jasonleaster.seckill.model.SuccessDeal;

/**
 * Author: Administrator
 * Date  : 2017/1/22
 * Email : jasonleaster@gmail.com
 * 封装秒杀执行后结果
 */
public class SecKillExcution {
    //秒杀id
    private long commodityId;
    //秒杀执行结果状态
    private int state;
    //状态表示
    private String stateInfo;
    //秒杀成功对象
    private SuccessDeal successDeal;

    public SecKillExcution(long commodityId, DealStateEnum stateEnum, SuccessDeal successDeal) {
        this.commodityId = commodityId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successDeal = successDeal;
    }

    public SecKillExcution(long commodityId, DealStateEnum stateEnum) {
        this.commodityId = commodityId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
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

    public SuccessDeal getSuccessDeal() {
        return successDeal;
    }

    public void setSuccessDeal(SuccessDeal successDeal) {
        this.successDeal = successDeal;
    }

    @Override
    public String toString() {
        return "SeckillExcution{" +
                "commodityId=" + commodityId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successDeal=" + successDeal +
                '}';
    }
}

