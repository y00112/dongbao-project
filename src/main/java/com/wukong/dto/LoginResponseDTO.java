package com.wukong.dto;

import com.wukong.pojo.UmsMember;
import lombok.Data;

/**
 * Created By WuKong on 2022/8/2 21:21
 **/
@Data
public class LoginResponseDTO {
    private String token;
    private UmsMember umsMember;
}
