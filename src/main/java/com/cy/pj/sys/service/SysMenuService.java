package com.cy.pj.sys.service;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;

import java.util.List;
import java.util.Map;

public interface SysMenuService {
    public List<Map<String,Object>> findObjects();

    public int deleteObjects(Integer id);

    public List<Node> findZtreeMenuNodes();

    public int insertObject(SysMenu node);

    public int updateObject(SysMenu sysMenu);

}
