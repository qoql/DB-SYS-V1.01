package com.cy.pj.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMenuDao {
    @Delete("delete from sys_role_menus where menu_id=#{menuId}")
    int deleteObjectsByMenuId(Integer menuId);
    @Delete("delete from sys_role_menus where role_id=#{roleId}")
    int deleteObjectsByRoleId(Integer roleId);


    int insertObjects(
            @Param("roleId")Integer roleId,
            @Param("menuIds")Integer[] menuIds);

    List<Integer> findMenuIdsByRoleIds(@Param("roleIds")Integer[] roleIds);
}
