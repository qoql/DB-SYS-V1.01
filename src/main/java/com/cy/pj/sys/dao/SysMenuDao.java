package com.cy.pj.sys.dao;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper//spring会为被该注解注释的接口创建代理对象
public interface SysMenuDao {

    public List<Map<String,Object>> findObjects();
    @Select("select count(*) from sys_menus where parentId=#{id}")
    public int getChildCount(Integer id);

    public int deleteObjects(Integer id);
    @Select("select id,name,parentId from sys_menus")
    public List<Node> findZtreeMenuNodes();

    public int insertObject(SysMenu sysMenu);

    public int updateObject(SysMenu sysMenu);

    public List<String> findPermission(@Param("menuIds")Integer[] menuIds);

}
