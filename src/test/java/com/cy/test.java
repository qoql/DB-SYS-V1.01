package com.cy;

import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class test {
    @Autowired
    SysLogDao sysLogDao;

    @Test
    public void sysRowCountTest(){

        System.out.println(sysLogDao.getRowCount(""));
    }

    @Test
    public void sysFindTest(){
        List<SysLog> list = sysLogDao.findPageObjects("a", 0, 4);
        for (SysLog s:
             list) {
            System.out.println(s);
        }
    }

}
