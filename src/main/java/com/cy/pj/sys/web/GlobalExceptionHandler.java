package com.cy.pj.sys.web;

import com.cy.pj.common.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j//@Slf4j注解告诉lombok插件为GlobalExceptionHandler自动添加一个log属性
@ControllerAdvice//使用此注解，则以下类为全局异常处理类
public class GlobalExceptionHandler {
    //
    //Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /*
        在此异常处理类中，我们对异常信息进行封装，定义异常处理方法，方法返回值为JsonResult
        JsonResult可以封装正常信息，也可以封装异常信息
        异常处理方法名为doHandlerRuntimeException，处理所有运行时异常，在方法中
        我们把异常信息做一个封装，封装的过程就是new一个JsonResult，传一个异常对象给JsonResult，
        也可以异常信息打印在控制台上，以便调试
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public JsonResult doHandlerRuntimeException(RuntimeException e){
        log.error(e.getMessage());
        System.out.println(log.getClass());
        e.printStackTrace();
        return  new JsonResult(e);

    }

    @ResponseBody
    @ExceptionHandler(ShiroException.class)
    public JsonResult doHandlerShiroException(ShiroException e){
        JsonResult r = new JsonResult();
        r.setState(0);
        if(e instanceof UnknownAccountException){
            r.setMessage("用户不存在");
        }
        else if(e instanceof LockedAccountException){
            r.setMessage("账户已被封禁");
        }
        else if(e instanceof IncorrectCredentialsException){
            r.setMessage("密码不正确");
        }
        else if(e instanceof AuthorizationException){
            r.setMessage("没有此操作权限");
        }else {
            r.setMessage("系统维护中");
        }
        e.printStackTrace();
        return r;
    }


}
