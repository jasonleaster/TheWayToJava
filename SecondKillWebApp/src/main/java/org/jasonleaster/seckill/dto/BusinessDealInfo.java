package org.jasonleaster.seckill.dto;

/**
 * 交易信息 (暴露秒杀地址DTO)
 * Author : jasonleaster
 * Email  : jasonleaster@gmail.com
 * Date   : 2017.01.22
 */
public class BusinessDealInfo {

    /**
     *  交易Id (秒杀id)
     */
    private long transactionId;

    /**
     * 系统当前时间（毫秒）
     */
    private long now;

    /**
     * 开启时间（毫秒）
     */
    private long start;

    /**
     * 结束时间
     */
    private long end;

    //是否开启秒杀
    private boolean exposed;
    //一种加密措施
    private String md5;

    public BusinessDealInfo(boolean exposed, String md5, long transactionId) {
        this.exposed        = exposed;
        this.md5            = md5;
        this.transactionId  = transactionId;
    }

    public BusinessDealInfo(boolean exposed, long transactionId, long now, long start, long end) {
        this.exposed        = exposed;
        this.transactionId  = transactionId;
        this.now            = now;
        this.start          = start;
        this.end            = end;
    }

    public BusinessDealInfo(boolean exposed, long transactionId) {
        this.exposed        = exposed;
        this.transactionId  = transactionId;
    }

    @Override
    public String toString() {
        return "BusinessDealInfo{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", transactionId=" + transactionId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
