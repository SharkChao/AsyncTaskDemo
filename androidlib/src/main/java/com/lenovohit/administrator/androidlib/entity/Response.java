package com.lenovohit.administrator.androidlib.entity;

/**
 * Created by SharkChao on 2017-07-18.
 * 从服务器返回的数据，统一起来
 */

public class Response {
    private  boolean isError;
    //客户端访问网络时的错误 用负数标识
    //服务器端自己的问题  则用正数标识
    private  int errorType;
    private String errorMessage;
    private String result;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
