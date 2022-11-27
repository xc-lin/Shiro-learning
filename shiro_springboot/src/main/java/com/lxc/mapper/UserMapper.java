package com.lxc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Frank_lin
 * @date 2022/11/27
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT NAME FROM role WHERE id IN (SELECT rid FROM role_user WHERE uid=(SELECT id FROM USER WHERE NAME=#{principal}))")
    List<String> getUserRoleInfoMapper(@Param("principal") String principal);


    @Select({ "<script>",
            "select info FROM permissions WHERE id IN ", "(SELECT pid FROM role_ps WHERE rid IN (", "SELECT id FROM role WHERE NAME IN ",
            "<foreach collection='roles' item='name' open='(' separator=',' close=')'>", "#{name}",
            "</foreach>", "))", "</script>"
            })
    List<String> getUserPermissionInfoMapper(@Param("roles")List<String> roles);
}
