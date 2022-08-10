package com.cy.pj.sys.controller;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/role/")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(
            String name,Integer pageCurrent){
        PageObject<SysRole> pageObject=
                sysRoleService.findPageObjects(name,pageCurrent);
        return new JsonResult(pageObject);
    }

    @ResponseBody
    @RequestMapping("doDeleteObject")
    public JsonResult doDeleteObject(Integer id){
        sysRoleService.deleteObject(id);
        return new JsonResult("delete ok");
    }

    @ResponseBody
    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysRole sysRole,Integer[] menuIds){
        int row = sysRoleService.saveObject(sysRole, menuIds);
        //System.out.println(sysRole);
        return new JsonResult("Insert ok");
    }

    @RequestMapping("doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(Integer id){
        return new JsonResult(sysRoleService.findObjectById(id));
    }


    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysRole entity, Integer[] menuIds) {
        sysRoleService.updateObject(entity, menuIds);
        return new JsonResult("update ok");

    }

    @RequestMapping("doFindRoles")
    @ResponseBody
    public JsonResult doFindRoles(){
        List<CheckBox> list = sysRoleService.findObjects();
        return new JsonResult(list);
    }

}
