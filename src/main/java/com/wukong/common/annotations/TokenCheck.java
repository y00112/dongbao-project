package com.wukong.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created By WuKong on 2022/8/2 20:45
 * token校验
 **/
@Target(ElementType.METHOD)  //作用在方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenCheck {

    //是否校验tokenn
    boolean required() default true;

}
