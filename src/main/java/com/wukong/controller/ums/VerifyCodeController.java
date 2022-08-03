package com.wukong.controller.ums;

import com.wukong.common.annotations.TokenCheck;
import com.wukong.pojo.ImageCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created By WuKong on 2022/8/3 20:46
 * 数字验证码
 **/
@RestController
@RequestMapping("/code")
public class VerifyCodeController {

    String attrName = "verifyCode";


    /**
     * 生成验证码
     */
    @TokenCheck(required = false)
    @GetMapping("/generator")
    public  void generatorCode(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("/code/generator");

        ImageCode imageCode = ImageCode.getInstance();

        //验证码的值
        String code = imageCode.getCode();

        request.getSession().setAttribute(attrName,code);

        //验证码图片
        ByteArrayInputStream image = imageCode.getImage();

        response.setContentType("image/jpeg");
        byte[] bytes = new byte[1024];
        try(ServletOutputStream out = response.getOutputStream()) {
            while (image.read(bytes) != -1){
                out.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 校验验证码
     * @param verifyCode
     * @return
     */
    @TokenCheck(required = false)
    @GetMapping("verify")
    public String verify(String verifyCode, HttpServletRequest request) {
        System.out.println("/code/verify");

        String str = request.getSession().getAttribute(attrName).toString();
        if (verifyCode.equals(str)) {
            return "验证码校验通过";
        }
        return "验证码校验不通过";
    }
}
