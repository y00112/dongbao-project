package com.wukong.common.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//统一返回值
public class ResultWrapper<T> implements Serializable {

    //状态码
    private int code;

    //提示信息
    private String msg;

    private  T data;


    public ResultWrapper getSuccessBuilder(int code, String msg, T data){
        return new ResultWrapper(code,msg,data);
    }

    public static ResultWrapper getErrorBuilder(int code,String msg){
        return new ResultWrapper(code,msg,"");
    }

//        //返回成功的包装
//    public static ResultWrapper.ResultWrapperBuilder getSuccessBuilder(){
//        return ResultWrapper.builder().code(StateCodeEnum.SUCCESS.getCode()).msg(StateCodeEnum.SUCCESS.getMsg());
//    }

   // 返回失败的包装
//    public static ResultWrapper.ResultWrapperBuilder getErrorBuilder(){
//        return ResultWrapper.builder().code(StateCodeEnum.ERROR.getCode()).msg(StateCodeEnum.ERROR.getMsg());
//    }

}
