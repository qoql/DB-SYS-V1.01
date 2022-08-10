package com.cy.pj.sys.service;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.util.PageUtil;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysUserDeptVo;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class SysUserServiceImp implements SysUserService{
    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysUserRoleDao sysUserRoleDao;

    @Override
    public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
        //1.数据合法性验证
        if(pageCurrent==null||pageCurrent<=0)
            throw new ServiceException("参数不合法");
        //2.依据条件获取总记录数
        int rowCount=sysUserDao.getRowCount(username);
        if(rowCount==0)
            throw new ServiceException("记录不存在");
        //3.计算startIndex的值
        int pageSize=3;
        int startIndex=(pageCurrent-1)*pageSize;
        //4.依据条件获取当前页数据
        List<SysUserDeptVo> records=sysUserDao.findPageObjects(
                username, startIndex, pageSize);
//        //5.封装数据
//        PageObject<SysUserDeptVo> pageObject=new PageObject<>();
//        pageObject.setPageCurrent(pageCurrent);
//        pageObject.setRowCount(rowCount);
//        pageObject.setPageSize(pageSize);
//        pageObject.setRecords(records);
//        pageObject.setPageCount((rowCount-1)/pageSize+1);
        return PageUtil.newInstance(rowCount,records,pageCurrent,pageSize);
    }

    @Override
    public int validById(Integer id, Integer valid, String modifiedUser) {
        if(id==null||id<1){
            throw new IllegalArgumentException("id无效");
        }
        if(valid==null||(valid!=0&&valid!=1)){
            throw new IllegalArgumentException("valid无效");
        }
        if(StringUtils.isEmpty(modifiedUser)){
            throw new ServiceException("修改用户不能为空");
        }
        int row = sysUserDao.validById(id, valid, modifiedUser);
        if(row==0){
            throw new ServiceException("记录不存在");
        }
        return row;
    }

    @Override
    public int saveObject(SysUser entity, Integer[] roleIds) {
        //1.验证数据合法性
        if(entity==null)
            throw new ServiceException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getUsername()))
            throw new ServiceException("用户名不能为空");
        if(StringUtils.isEmpty(entity.getPassword()))
            throw new ServiceException("密码不能为空");
        if(roleIds==null || roleIds.length==0)
            throw new ServiceException("至少要为用户分配角色");

        //2.将数据写入数据库
        String salt= UUID.randomUUID().toString();
        entity.setSalt(salt);
        //加密(先了解,讲shiro时再说)
        SimpleHash sHash=
                new SimpleHash("MD5",entity.getPassword(), salt,1);
        entity.setPassword(sHash.toHex());

        int rows=sysUserDao.insertObject(entity);
        sysUserRoleDao.insertObjects(
                entity.getId(),
                roleIds);//"1,2,3,4";
        //3.返回结果
        return rows;
    }

    @Override
    public Map<String, Object> findObjectById(Integer userId) {
        //1.合法性验证
        if(userId==null||userId<=0)
            throw new ServiceException(
                    "参数数据不合法,userId="+userId);
        //2.业务查询
        SysUserDeptVo user=
                sysUserDao.findObjectById(userId);
        if(user==null)
            throw new ServiceException("此用户已经不存在");
        List<Integer> roleIds=
                sysUserRoleDao.findRoleIdsByUserId(userId);
        //3.数据封装
        Map<String,Object> map=new HashMap<>();
        map.put("user", user);
        map.put("roleIds", roleIds);
        return map;
    }


    @Override
    public int updateObject(SysUser entity,Integer[] roleIds) {
        //1.参数有效性验证
        if(entity==null)
            throw new IllegalArgumentException("保存对象不能为空");
        if(StringUtils.isEmpty(entity.getUsername()))
            throw new IllegalArgumentException("用户名不能为空");
        if(roleIds==null||roleIds.length==0)
            throw new IllegalArgumentException("必须为其指定角色");
        //其它验证自己实现，例如用户名已经存在，密码长度，...
        //2.更新用户自身信息
        int rows=sysUserDao.updateObject(entity);
        //3.保存用户与角色关系数据
        sysUserRoleDao.deleteObjectsByUserId(entity.getId());
        sysUserRoleDao.insertObjects(entity.getId(),
                roleIds);
        //4.返回结果
        return rows;
    }

    @Override                 //原密码          新密码                确定新密码
    public int updatePassword(String password, String newPassword, String cfgPassword) {
//        System.out.println(newPassword);
//        System.out.println(password);
//        System.out.println(cfgPassword);
        if(StringUtils.isEmpty(newPassword)){
            throw new IllegalArgumentException("新密码不能为空");
        }
        if(StringUtils.isEmpty(cfgPassword)){
            throw new IllegalArgumentException("确定密码不能为空");
        }
        //判断新密码与确定新密码是否相同
        if(!cfgPassword.equals(newPassword)){
            throw new IllegalArgumentException("两次输入的新密码不一致");
        }
        if(StringUtils.isEmpty(password)){
            throw new IllegalArgumentException("原密码不能为空");
        }
        //获取登录用户
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        //对页面上原密码进行加密
        SimpleHash hash = new SimpleHash("MD5", password, user.getSalt(),1);
        //对比页面上的原密码和数据库中的用户密码
        if(!user.getPassword().equals(hash.toHex())){
            throw new IllegalArgumentException("原密码不正确");
        }
        //对新密码加密
        //1.获取盐值
        String sale = UUID.randomUUID().toString();
        //2.加密
        hash = new SimpleHash("MD5",newPassword,sale,1);
        //将新密码更新到数据库
        int row = sysUserDao.updatePassword(hash.toHex(), sale, user.getId());
        if(row==0){
            throw new ServiceException("修改失败");
        }
        return row;
    }

}
