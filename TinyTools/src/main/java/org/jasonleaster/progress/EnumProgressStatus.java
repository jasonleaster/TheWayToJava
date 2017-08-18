package org.jasonleaster.progress;

/**
 * Author: jasonleaster
 * Date  : 2017/8/17
 * Email : jasonleaster@gmail.com
 * Description:
 *     进度条状态枚举类
 */
public enum EnumProgressStatus {

    START("start"),

    RUNNING("running"),

    INTERRUPTED("interrupted"),

    CANCELED("canceled"),

    FINISHED("finished");

    private String status;

    EnumProgressStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
