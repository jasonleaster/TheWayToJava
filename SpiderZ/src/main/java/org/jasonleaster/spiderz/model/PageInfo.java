package org.jasonleaster.spiderz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: jasonleaster
 * Date  : 2017/5/27
 * Email : jasonleaster@gmail.com
 * Description:
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class PageInfo {

    @JsonProperty("is_start")
    private boolean isStart;

    @JsonProperty("is_end")
    private boolean isEnd;

    @JsonProperty("totals")
    private int totals;

    @JsonProperty("previous")
    private String previous;

    @JsonProperty("next")
    private String next;

    public boolean getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(boolean end) {
        isEnd = end;
    }

    public int getTotals() {
        return totals;
    }

    public void setTotals(int totals) {
        this.totals = totals;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public boolean getIsStart() {
        return isStart;
    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
