package com.cy;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysLogServiceTests {
    @Autowired
    private SysLogService service;
    @Test
    public void testFindPageObjects(){
        PageObject<SysLog> po = service.findPageObjects("a",1);
        System.out.println(po.getPageCount());
        System.out.println(po.getRecords().size());
    }

}
