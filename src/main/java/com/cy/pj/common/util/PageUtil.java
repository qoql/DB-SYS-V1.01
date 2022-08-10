package com.cy.pj.common.util;

import com.cy.pj.common.vo.PageObject;

import java.util.List;

public class PageUtil {
    public static <T>PageObject<T> newInstance(Integer rowCount, List<T> records, Integer pageCurrent, Integer pageSize){
        return new PageObject(rowCount,records,pageCurrent,pageSize);
    }

}
