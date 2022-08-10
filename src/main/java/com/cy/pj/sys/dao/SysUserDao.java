package com.cy.pj.sys.dao;

import com.cy.pj.common.vo.SysUserDeptVo;
import com.cy.pj.sys.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysUserDao {
    public List<SysUserDeptVo> findPageObjects(@Param("username") String username, @Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    public int getRowCount(@Param("username") String username);

    public int validById(@Param("id")Integer id,
                         @Param("valid")Integer valid,
                         @Param("modifiedUser")String modifiedUser
                         );

    public SysUserDeptVo findObjectById(Integer id);

    public int insertObject(SysUser sysUser);

    public int updateObject(SysUser entity);
    @Select("select * from sys_users where username=#{username}")
    public SysUser findUserByUserName(String name);

    /**
     * 基于用户id修改用户的密码
     * @param password 新密码
     * @param salt 盐值
     * @param id 用户id
     * @return
     */
    @Update("update sys_users set password=#{password},salt=#{salt},modifiedTime=now() where id=#{id}")
    public int updatePassword(@Param("password")String password,
                              @Param("salt")String salt,
                              @Param("id")Integer id);


}
