package org.jasonleaster.seckill.dto;

/**
 * Author : jasonleaster
 * Email  : jasonleaster@gmail.com
 * Date   : 2017.01.22
 * Description:
 *      所有ajax请求返回类型，封装json结果
 */

public class RestfulResponse<T> {
    private boolean success;
    private String error;
    private T data;

    public RestfulResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public RestfulResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

