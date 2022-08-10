package com.cy.pj.sys.service;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysRoleMenuVo;
import com.cy.pj.sys.entity.SysRole;

import java.util.List;

public interface SysRoleService {
    /**
     * 本方法中要分页查询角色信息,并查询角色总记录数据
     * @param pageCurrent 当表要查询的当前页的页码值
     * @return 封装当前实体数据以及分页信息
     */
    PageObject<SysRole> findPageObjects(
            String name,Integer pageCurrent);

    public int deleteObject(Integer id);

    public int saveObject(SysRole sysRole,Integer[] menuIds);

    public SysRoleMenuVo findObjectById(Integer id);

    public int updateObject(SysRole sysRole,Integer[] menuIds);

    public List<CheckBox> findObjects();
}
