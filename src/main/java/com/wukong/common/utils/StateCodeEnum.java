package com.wukong.common.utils;

public enum StateCodeEnum {

    //请求成功
    SUCCESS(200,"请求成功");

    //请求失败
//    ERROR(500,"请求失败");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    StateCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
