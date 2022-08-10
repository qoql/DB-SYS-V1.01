package com.cy.pj.sys.service;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;

public interface SysLogService {
    /**
     * 基于条件分页查询日志信息
     * @param username  查询条件（例如查询哪个用户的日志信息）
     * @return    当前页的日志记录信息
     */
    public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent);

    public int deleteObjects(Integer ... ids);

}
