package com.cy.pj.sys.dao;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.SysRoleMenuVo;
import com.cy.pj.sys.entity.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRoleDao {
    /**
     * 分页查询角色信息
     * @param startIndex 上一页的结束位置
     * @param pageSize 每页要查询的记录数
     * @return
     */
    List<SysRole> findPageObjects(
            @Param("name")String name,
            @Param("startIndex")Integer startIndex,
            @Param("pageSize")Integer pageSize);
    /**
     * 查询记录总数
     * @return
     */
    int getRowCount(@Param("name")String name);

    @Delete("delete from sys_roles where id=#{id}")
    int deleteObject(Integer id);

    public int insertObject(SysRole sysRole);

    public SysRoleMenuVo findObjectById(Integer id);

    public int updateObject(SysRole sysRole);
    @Select("select id,name from sys_roles")
    public List<CheckBox> findObjects();

}
