package com.wukong.controller.ums;

import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaStyle;
import com.ramostear.captcha.support.CaptchaType;
import com.wukong.common.annotations.TokenCheck;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created By WuKong on 2022/8/5 23:36
 **/
@RestController
@RequestMapping("happy-captcha")
public class HappyCaptchaController {

    @GetMapping("/generator")
    @TokenCheck(required = false)
    public void generatorCode(HttpServletRequest request, HttpServletResponse response) {
        HappyCaptcha.require(request,response)
                .style(CaptchaStyle.ANIM)
                .type(CaptchaType.ARITHMETIC_ZH)
                .build()
                .finish();
    }

    @GetMapping("/verify")
    @TokenCheck(required = false)
    public String verify(String verifyCode,HttpServletRequest request) {
        boolean verification = HappyCaptcha.verification(request, verifyCode, true);

        if (verification) {
            return "验证码正确";
        }else {
            return "验证码错误";
        }

    }

}
