package com.wukong.controller.ums;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
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
import java.util.Base64;

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


    @TokenCheck(required = false)
    @GetMapping("/generator-base64")
    public String generatorCodeBase64(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("/code/generator-base64");

        ImageCode imageCode = ImageCode.getInstance();

        //验证码的值
        String code = imageCode.getCode();


        //验证码图片
        ByteArrayInputStream image = imageCode.getImage();

        request.getSession().setAttribute(attrName,code);

        ByteOutputStream swapStream = new ByteOutputStream();
        byte[] buff = new byte[1024];
        int r = 0;
        while ((r=image.read(buff,0,1024))>0){
            swapStream.write(buff,0,r);
        }

        byte[] data = swapStream.toByteArray();

        return Base64.getEncoder().encodeToString(data);


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
