package org.jasonleaster.progress;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: jasonleaster
 * Date  : 2017/8/17
 * Email : jasonleaster@gmail.com
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
class BasicProgressInfo extends AbstractProgressInfo implements Serializable{

    private String progressName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public BasicProgressInfo(String progressId) {
        this.progressId = progressId;
        this.progressName = "ProgressNameFor : " + progressId;
        this.status = EnumProgressStatus.NOTSTARTED;
        this.value  = 0.0;
    }

    public BasicProgressInfo(String progressId, String progressName) {
        this.progressId = progressId;
        this.progressName = progressName;
        this.status = EnumProgressStatus.NOTSTARTED;
        this.value  = 0.0;
    }

    public BasicProgressInfo(BasicProgressInfo other){
        this.progressId = other.progressId;
        this.progressName = other.progressName;
        this.value = other.value;
        this.status = other.status;
        this.startTime = other.startTime;
        this.endTime   = other.endTime;

        // Attention! But we don't copy the list
        // We will do it later
    }
}
