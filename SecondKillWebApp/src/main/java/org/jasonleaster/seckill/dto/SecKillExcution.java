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
    private long seckillId;
    //秒杀执行结果状态
    private int state;
    //状态表示
    private String stateInfo;
    //秒杀成功对象
    private SuccessDeal successDeal;

    public SecKillExcution(long seckillId, DealStateEnum stateEnum, SuccessDeal successDeal) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.successDeal = successDeal;
    }

    public SecKillExcution(long seckillId, DealStateEnum stateEnum) {
        this.seckillId = seckillId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
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

    public SuccessDeal getSuccessDeal() {
        return successDeal;
    }

    public void setSuccessDeal(SuccessDeal successDeal) {
        this.successDeal = successDeal;
    }

    @Override
    public String toString() {
        return "SeckillExcution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successDeal=" + successDeal +
                '}';
    }
}

