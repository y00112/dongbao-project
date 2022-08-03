package com.wukong.service.ums;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wukong.common.utils.ResultWrapper;
import com.wukong.dto.ums.UmsMemberLoginParamDTO;
import com.wukong.dto.ums.UmsMemberRegisterParamDTO;
import com.wukong.pojo.UmsMember;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 悟空
 * @since 2022-06-15
 */
public interface UmsMemberService extends IService<UmsMember> {
    ResultWrapper register(UmsMemberRegisterParamDTO umsMemberRegisterParamDTO);

    ResultWrapper login(UmsMemberLoginParamDTO umsMemberLoginParamDTO);

    ResultWrapper update(UmsMember umsMember);
}
