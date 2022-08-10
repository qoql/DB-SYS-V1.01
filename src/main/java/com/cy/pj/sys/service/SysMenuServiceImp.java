package com.cy.pj.sys.service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.entity.SysMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class SysMenuServiceImp implements SysMenuService{
    @Autowired
    SysMenuDao sysMenuDao;
    
    @Autowired
    SysRoleMenuDao sysRoleMenuDao;

    @Override
    public List<Map<String,Object>> findObjects(){
        List<Map<String, Object>> list = sysMenuDao.findObjects();
        //结果校验
        if(list==null||list.size()==0){
            log.error("没有对应的菜单信息");
            throw new ServiceException("没有对应的菜单信息");
        }

        return list;
    }

    @Override
    public int deleteObjects(Integer id) {
        if(id==null || id<1){
            throw new IllegalArgumentException("id值无效");
        }
        int childCount = sysMenuDao.getChildCount(id);
        if(childCount>0){
            throw new ServiceException("请先删除子菜单");
        }
        sysRoleMenuDao.deleteObjectsByMenuId(id);
        int rows = sysMenuDao.deleteObjects(id);
        if(rows==0){
            throw new ServiceException("该记录可能不存在");
        }
        return rows;
    }


    @Override
    public List<Node> findZtreeMenuNodes() {
        return sysMenuDao.findZtreeMenuNodes();
    }

    @Override
    public int insertObject(SysMenu sysMenu) {
        if(sysMenu==null){
            throw new IllegalArgumentException("添加对象不能为空");
        }
        if (StringUtils.isEmpty(sysMenu.getName())){
            throw new ServiceException("菜单名不能为空");
        }

        int row = sysMenuDao.insertObject(sysMenu);
        if(row==0){
            throw new ServiceException("保存失败");
        }
        return row;
    }

    @Override
    public int updateObject(SysMenu sysMenu) {
        if(sysMenu==null){
            throw new IllegalArgumentException("保存对象不能为空");
        }
        if(StringUtils.isEmpty(sysMenu.getName())){
            throw new ServiceException("菜单名不能为空");
        }
        int row = sysMenuDao.updateObject(sysMenu);
        if(row==0){
            throw new ServiceException("修改失败，记录可能不存在");
        }
        return row;
    }
}
