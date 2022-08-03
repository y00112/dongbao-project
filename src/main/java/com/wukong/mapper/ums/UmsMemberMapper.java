package com.wukong.mapper.ums;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wukong.pojo.UmsMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 悟空
 * @since 2022-06-15
 */
@Mapper
public interface UmsMemberMapper extends BaseMapper<UmsMember> {

    @Select("SELECT * FROM `ums_member` WHERE username = #{username}")
    UmsMember selectByName(String username);
}
