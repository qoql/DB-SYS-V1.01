package com.cy.pj.sys.controller;

import com.cy.pj.common.util.ShiroUtils;
import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.common.vo.SysUserDeptVo;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequestMapping("/user/")
@Controller
public class SysUserController {
    @Autowired
    SysUserService service;

    @ResponseBody
    @RequestMapping("doLogin")
    public JsonResult doLogin(String username,String password,boolean isRememberMe){
        System.out.println(isRememberMe);
//        System.out.println("111111------------------");
        //1.获取Subject对象（主题对象，负责提交用户信息）
        Subject subject = SecurityUtils.getSubject();
        //2.提交用户信息
        //2.1 封装用户信息
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //判断“记住我”按钮是否被选取
        if(isRememberMe){
            //令牌添加“记住我”
            token.setRememberMe(true);
        }
        //2.2 提交token（提交给SecurityManager）
        subject.login(token);
        return new JsonResult("login ok");
    }

    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(String username,Integer pageCurrent){
        PageObject<SysUserDeptVo> pageObjects = service.findPageObjects(username, pageCurrent);
        return new JsonResult(pageObjects);
    }

    @RequestMapping("doValidById")
    @ResponseBody
    public JsonResult doValidById(Integer id,Integer valid){
        service.validById(id, valid, ShiroUtils.getUsername());
        return new JsonResult("update ok");
    }

    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(
            SysUser entity,
            Integer[] roleIds){
        service.saveObject(entity,roleIds);
        return new JsonResult("save ok");
    }


    @RequestMapping("doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(
            Integer id){
        Map<String,Object> map= service.findObjectById(id);
        return new JsonResult(map);
    }

    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(
            SysUser entity,Integer[] roleIds){
        service.updateObject(entity, roleIds);
        return new JsonResult("update ok");
    }

    @RequestMapping("doUpdatePassword")
    @ResponseBody
    public JsonResult doUpdatePassword(String pwd, String newPwd, String cfgPwd){
//        System.out.println(newPwd);
//        System.out.println(pwd);
//        System.out.println(cfgPwd);
        service.updatePassword(pwd,newPwd,cfgPwd);
        return new JsonResult("updatePassword ok");
    }




}
