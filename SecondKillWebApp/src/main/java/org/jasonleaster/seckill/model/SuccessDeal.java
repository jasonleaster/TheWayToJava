package org.jasonleaster.seckill.model;

import java.util.Date;

public class SuccessDeal extends SuccessDealKey {
    private Byte state;

    private Date createTime;

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}