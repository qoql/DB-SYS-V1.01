package com.cy.pj.sys.dao;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysDeptDao {
    @Select("select * from sys_depts where id=#{id}")
    public SysDept findById(Integer id);
    @Select("select id,name,parentId from sys_depts")
    public List<Node> findZTreeNodes();
}
