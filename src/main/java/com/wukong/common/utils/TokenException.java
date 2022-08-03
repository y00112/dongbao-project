package com.wukong.common.utils;

/**
 * Created By WuKong on 2022/8/2 21:52
 * 自定义token异常
 **/
public class TokenException extends Exception{
    public TokenException(String message) {
        super(message);
    }
}
