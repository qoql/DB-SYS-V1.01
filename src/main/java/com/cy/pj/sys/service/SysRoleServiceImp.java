package com.cy.pj.sys.service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.PageUtil;
import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysRoleMenuVo;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class SysRoleServiceImp implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public PageObject<SysRole> findPageObjects(
            String name, Integer pageCurrent) {
        //1.验证参数合法性
        //1.1验证pageCurrent的合法性，
        //不合法抛出IllegalArgumentException异常
        if (pageCurrent == null || pageCurrent < 1)
            throw new IllegalArgumentException("当前页码不正确");
        //2.基于条件查询总记录数
        //2.1) 执行查询
        int rowCount = sysRoleDao.getRowCount(name);
        //2.2) 验证查询结果，假如结果为0不再执行如下操作
        if (rowCount == 0)
            throw new ServiceException("记录不存在");
        //3.基于条件查询当前页记录(pageSize定义为2)
        //3.1)定义pageSize
        int pageSize = 2;
        //3.2)计算startIndex
        int startIndex = (pageCurrent - 1) * pageSize;
        //3.3)执行当前数据的查询操作
        List<SysRole> records =
                sysRoleDao.findPageObjects(name, startIndex, pageSize);
        //4.对分页信息以及当前页记录进行封装
//        //4.1)构建PageObject对象
//        PageObject<SysRole> pageObject = new PageObject<>();
//        //4.2)封装数据
//        pageObject.setPageCurrent(pageCurrent);
//        pageObject.setPageSize(pageSize);
//        pageObject.setRowCount(rowCount);
//        pageObject.setRecords(records);
//        pageObject.setPageCount((rowCount - 1) / pageSize + 1);
        //5.返回封装结果。
        return PageUtil.newInstance(rowCount,records,pageCurrent,pageSize);
    }

    @Override
    public int deleteObject(Integer id) {
        if(id==null||id<1){
            throw new IllegalArgumentException("id值不正确，id="+id);
        }
        int row = sysRoleDao.deleteObject(id);
        if(row==0){
            throw new ServiceException("删除失败，记录可能不存在");
        }
        sysRoleMenuDao.deleteObjectsByRoleId(id);
        sysUserRoleDao.deleteObjectsByRoleId(id);
        return row;
    }

    @Override
    public int saveObject(SysRole sysRole, Integer[] menuIds) {
        if(sysRole==null){
            throw new IllegalArgumentException("请填写角色信息");
        }
        if(StringUtils.isEmpty(sysRole.getName())){
            throw new ServiceException("角色名不能为空");
        }
        int row = sysRoleDao.insertObject(sysRole);
        sysRoleMenuDao.insertObjects(sysRole.getId(),menuIds);
        return row;
    }

    @Override
    public SysRoleMenuVo findObjectById(Integer id) {
        //1.合法性验证
        if(id==null||id<=0)
            throw new ServiceException("id的值不合法");
        //2.执行查询
        SysRoleMenuVo result=sysRoleDao.findObjectById(id);
        //3.验证结果并返回
        if(result==null)
            throw new ServiceException("此记录已经不存在");
        return result;
    }

    @Override
    public int updateObject(SysRole sysRole, Integer[] menuIds) {
        if(sysRole==null){
            throw new IllegalArgumentException("更新对象不能为空");
        }
        if(StringUtils.isEmpty(sysRole.getName())){
            throw new ServiceException("角色名不能为空");
        }
        int row = sysRoleDao.updateObject(sysRole);
        if(row==0){
            throw new ServiceException("更新失败");
        }
        sysRoleMenuDao.deleteObjectsByRoleId(sysRole.getId());
        sysRoleMenuDao.insertObjects(sysRole.getId(),menuIds);
        return row;
    }

    @Override
    public List<CheckBox> findObjects() {
        List<CheckBox> objects = sysRoleDao.findObjects();
        if(objects==null||objects.size()==0){
            throw new ServiceException("系统没有角色信息");
        }
        return objects;
    }

}