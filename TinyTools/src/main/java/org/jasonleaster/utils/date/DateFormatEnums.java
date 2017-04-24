package org.jasonleaster.utils.date;

/**
 * Author: jasonleaster
 * Date  : 2017/4/24
 * Email : jasonleaster@gmail.com
 */
public enum DateFormatEnums {

    DATE_FORMAT_I("YYYY-MM-DD"),
    DATE_FORMAT_II("YYYY/MM/DD"),
    DATE_FORMAT_III(""),
    DATE_FORMAT_IV(""),
    DATE_FORMAT_V(""),
    DATE_FORMAT_VI(""),
    DATE_FORMAT_VII(""),
    DATE_FORMAT_VIII("");
    ;

    DateFormatEnums(String format) {
        this.format = format;
    }

    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
