package com.cy.pj.sys.dao;

import com.cy.pj.sys.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysLogDao {
    /**
     * 基于条件分页查询日志信息
     * @param username  查询条件（例如查询哪个用户的日志信息）
     * @param startIndex    当前页的起始位置
     * @param pageSize    当前页的页面大小
     * @return    当前页的日志记录信息
     */
    public List<SysLog> findPageObjects(@Param("username")String username,
                                        @Param("startIndex")Integer startIndex,
                                        @Param("pageSize")Integer pageSize);

    /**
     * 基于条件查询总记录数
     * @param username 查询条件（例如查询哪个用户的日志信息）
     * @return 总记录数（基于这个结果可以计算总页数）
     */
    public int getRowCount(@Param("username")String username);

    public int deleteObjects(@Param("ids") Integer ... ids);

}
