package com.cy.pj.sys.controller;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@RequestMapping("/menu/")
@Controller
public class SysMenuController {
    @Autowired
    SysMenuService service;

    @ResponseBody
    @RequestMapping("doFindObjects")
    public JsonResult doFindObjects(){
        List<Map<String, Object>> list = service.findObjects();
        return new JsonResult(list);
    }

    @ResponseBody
    @RequestMapping("doDeleteObject")
    public JsonResult doDeleteObject(Integer id){
        service.deleteObjects(id);
        return new JsonResult("delete ok");
    }

    @ResponseBody
    @RequestMapping("doFindZtreeMenuNodes")
    public JsonResult doFindZtreeMenuNodes(){
        List<Node> nodes = service.findZtreeMenuNodes();
        return new JsonResult(nodes);
    }

    @ResponseBody
    @RequestMapping("doSaveObject")
    public JsonResult doSaveObject(SysMenu sysMenu){
        service.insertObject(sysMenu);
        return new JsonResult("Insert ok");
    }

    @ResponseBody
    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysMenu sysMenu){
        service.updateObject(sysMenu);
        return new JsonResult("update ok");
    }

}
