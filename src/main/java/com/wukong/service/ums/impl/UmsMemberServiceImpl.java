package com.wukong.service.ums.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wukong.common.utils.JwtUtil;
import com.wukong.common.utils.ResultWrapper;
import com.wukong.dto.LoginResponseDTO;
import com.wukong.dto.ums.UmsMemberLoginParamDTO;
import com.wukong.dto.ums.UmsMemberRegisterParamDTO;
import com.wukong.mapper.ums.UmsMemberMapper;
import com.wukong.pojo.UmsMember;
import com.wukong.service.ums.UmsMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 悟空
 * @since 2022-06-15
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Override
    public ResultWrapper register(UmsMemberRegisterParamDTO umsMemberRegisterParamDTO) {
        UmsMember umsMember = new UmsMember();
        //将 umsMemberRegisterParamDTO 转换成 umsMember能保存的参数
        BeanUtils.copyProperties(umsMemberRegisterParamDTO,umsMember);

        //判断是用户是否重复注册
        UmsMember u1 = umsMemberMapper.selectByName(umsMemberRegisterParamDTO.getUsername());
        if (null != u1) {
            return ResultWrapper.getErrorBuilder(500,"用户重复注册");
        }

        //md5脱敏
        String encode = DigestUtils.md5DigestAsHex(umsMemberRegisterParamDTO.getPassword().getBytes());
        umsMember.setPassword(encode);
        int insert = umsMemberMapper.insert(umsMember);
        if (insert >0){
            return new ResultWrapper().getSuccessBuilder(200,"注册成功",umsMember);
        }
        return ResultWrapper.getErrorBuilder(500,"注册失败");
    }

    @Override
    public ResultWrapper login(UmsMemberLoginParamDTO umsMemberLoginParamDTO) {
        UmsMember umsMember = umsMemberMapper.selectByName(umsMemberLoginParamDTO.getUsername());

        if (null != umsMember){
            //将用户的密码使用md5加密，返回一个密文
            String digest = DigestUtils.md5DigestAsHex(umsMemberLoginParamDTO.getPassword().getBytes());
            //比较用户输入的密码与数据库密码是否相同
            if (!umsMember.getPassword().equals(digest)){
                return ResultWrapper.getErrorBuilder(500,"密码不正确");
            }
        }else {
            return ResultWrapper.getErrorBuilder(500,"用户名不存在");
        }

        //使用登录的密码创建一个 token
        String token = JwtUtil.createToken(umsMember.getUsername());

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken(token);
        loginResponseDTO.setUmsMember(umsMember);

        return new ResultWrapper().getSuccessBuilder(200,"登录成功",loginResponseDTO);
    }

    @Override
    public ResultWrapper update(UmsMember umsMember) {
        umsMemberMapper.updateById(umsMember);
        return new ResultWrapper().getSuccessBuilder(200,"修改成功",umsMember);
    }
}
