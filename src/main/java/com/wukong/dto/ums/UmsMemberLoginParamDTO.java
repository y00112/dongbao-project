package com.wukong.dto.ums;

/**
 * 接收前段传来的参数
 */

public class UmsMemberLoginParamDTO {
    private String username;
    private String password;

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
}
