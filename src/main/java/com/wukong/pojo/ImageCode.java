package com.wukong.pojo;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.Data;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created By WuKong on 2022/8/3 20:50
 * 验证码
 **/
@Data
public class ImageCode {

    //图形中的内容
    private String code;

    //图片
    private ByteArrayInputStream image;

    //图片的宽度
    private int width = 400;

    //图片的高度
    private int height = 100;

    //单例获得图形对象
    public static ImageCode getInstance() {
        return new ImageCode();
    }

    //构造器生成验证码
    private ImageCode() {

        //1、图形缓存区
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //2、创建一个画笔
        Graphics graphics = image.getGraphics();

        //3、拿笔画图形
        graphics.setColor(new Color(17,147,47));

        //4、画矩形
        graphics.fillRect(0,0,width,height);

        //5、画字体
        graphics.setFont(new Font("微软雅黑",Font.BOLD,30));

        //6、画字
        Random random = new Random();
        code = "";
        for (int i = 0; i < 6; i++) {
            String s = String.valueOf(random.nextInt(10));
            code += s;

            graphics.setColor(new Color(225,154,12));
            graphics.drawString(s,(width/6)*i,40);

            //划线
        }

        //7、收笔
        graphics.dispose();

        ByteArrayInputStream inputStream = null;
        ByteOutputStream outputStream = new ByteOutputStream();
        //赋值给byteArrayInputStream
        try {
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            ImageIO.write(image,"jpeg",imageOutputStream);

            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.image = inputStream;
    }
}
