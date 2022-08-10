package com.cy.pj.sys.service;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysDeptDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDeptServiceImp implements SysDeptService{
    @Autowired
    SysDeptDao sysDeptDao;


    @Override
    public List<Node> findZTreeNodes() {
        return sysDeptDao.findZTreeNodes();
    }
}
