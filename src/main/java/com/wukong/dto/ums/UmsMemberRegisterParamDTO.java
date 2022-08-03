package com.wukong.dto.ums;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 接收前段传来的参数
 */
public class UmsMemberRegisterParamDTO {

    @Size(min = 1,max = 8,message = "用户名长度在1—8之间")
    private String username;

    private String password;

    private String icon;

    @Email
    private String email;

    private String nickName;

    public UmsMemberRegisterParamDTO() {
    }

    public UmsMemberRegisterParamDTO(String username, @NotEmpty(message = "密码不能为空") String password, String icon, String email, String nickName) {
        this.username = username;
        this.password = password;
        this.icon = icon;
        this.email = email;
        this.nickName = nickName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
