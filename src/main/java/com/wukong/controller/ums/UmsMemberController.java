package com.wukong.controller.ums;
import com.wukong.common.annotations.TokenCheck;
import com.wukong.common.utils.JwtUtil;
import com.wukong.common.utils.ResultWrapper;
import com.wukong.dto.LoginResponseDTO;
import com.wukong.dto.ums.UmsMemberLoginParamDTO;
import com.wukong.dto.ums.UmsMemberRegisterParamDTO;
import com.wukong.pojo.UmsMember;
import com.wukong.service.ums.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 悟空
 * @since 2022-06-15
 */
@RestController
@RequestMapping("/ums-member")
public class UmsMemberController {

    @Autowired
    UmsMemberService umsMemberService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }


    /**
     * 注册功能
     * @param umsMemberRegisterParamDTO
     * @return
     */
    @PostMapping("/register")
    public ResultWrapper register(@RequestBody @Valid UmsMemberRegisterParamDTO umsMemberRegisterParamDTO){
        System.out.println("/ums-member/register");
        return umsMemberService.register(umsMemberRegisterParamDTO);
    }

    /**
     * 登录功能
     * @param umsMemberLoginParamDTO
     * @return
     */
    @PostMapping("/login")
    public ResultWrapper login(@RequestBody UmsMemberLoginParamDTO umsMemberLoginParamDTO){
        System.out.println("/ums-member/login");
        return umsMemberService.login(umsMemberLoginParamDTO);
    }

    /**
     * 测试token登录
     * @param token
     * @return
     */
    @GetMapping("/test-verify")
    public String verify(String token) {
//        测试token登录
//        String s = JwtUtil.parseToken(token);

        //token延期
        String token1 = JwtUtil.createToken(token);
        return token1;
    }

    /**
     * 更新信息
     * @param umsMember
     * @return
     */
    @PostMapping("/update")
    @TokenCheck
    public ResultWrapper update(@RequestBody UmsMember umsMember) {
        System.out.println("/ums-member/update");
        return umsMemberService.update(umsMember);
    }

}

