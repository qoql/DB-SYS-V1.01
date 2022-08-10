package com.cy.pj.sys.service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.PageUtil;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    业务层对象
    1）处理核心业务
    2）处理非核心业务（日志、权限、缓存...）
 */

@Service
public class SysLogServiceImp implements SysLogService{
    @Autowired
    private SysLogDao sysLogDao;


    @Override
    public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
        //1、验证当前页码值是否合法
        if(pageCurrent==null || pageCurrent<1){
            throw new IllegalArgumentException("当前页码值不正确");
        }
        //2、基于用户名查询总记录数并进行校验
        int rowCount = sysLogDao.getRowCount(username);
        if(rowCount==0){
            throw new ServiceException("系统没有查到对应记录");
        }
        //3、查询当前记录
        Integer pageSize = 3;
        Integer startIndex = (pageCurrent-1)*pageSize;
        List<SysLog> records = sysLogDao.findPageObjects(username,startIndex,pageSize);
        //4、封装查询结果并返回
        //计算总页数
//        int PageCount = rowCount/pageSize;
//        if(rowCount%pageSize!=0){
//            PageCount++;
//        }

        return  PageUtil.newInstance(rowCount,records,pageCurrent,pageSize);
    }

    // @RequiresPermissions("权限")表示需要有该权限才能使用此service
    @RequiresPermissions("sys:log:delete")
    @Override
    public int deleteObjects(Integer... ids) {
        //参数校验
        if(ids==null || ids.length==0){
            throw new IllegalArgumentException("请先勾选要删除记录");
        }
        int rows;
        try {
            rows = sysLogDao.deleteObjects(ids);
        }catch (Throwable e){
            e.printStackTrace();
            throw new ServiceException("系统故障");
        }
        //结果校验
        if(rows==0){
            throw new ServiceException("记录可能已经不存在");
        }
        return rows;
    }
}
