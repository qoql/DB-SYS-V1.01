package com.cy.pj.sys.service;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysUserDeptVo;
import com.cy.pj.sys.entity.SysUser;

import java.util.Map;

public interface SysUserService {

    PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent);

    int validById(Integer id,Integer valid,String modifiedUser);

    int saveObject(SysUser entity, Integer[] roleIds);

    Map<String,Object> findObjectById(Integer userId);

    int updateObject(SysUser entity,Integer[] roleIds);

    int updatePassword(String password,String newPassword,String cfgPassword);
}
