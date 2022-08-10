package com.cy.pj.sys.service.realm;

import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShiroUserRealm extends AuthorizingRealm {
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    SysUserRoleDao sysUserRoleDao;
    @Autowired
    SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    SysMenuDao sysMenuDao;
    /**获取授权信息并封装返回*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.获取登录用户id
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        Integer userId = user.getId();
        //2.基于用户id获取对应的角色id并进行校验
        Integer[] array = {};
        List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
        if(roleIds==null||roleIds.size()==0){
            throw new AuthenticationException();
        }
        //3.基于角色id获取菜单id并进行校验
        List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(array));
        if(menuIds==null||menuIds.size()==0){
            throw new AuthenticationException();
        }
        //4.基于菜单id获取对应的权限标识并进行校验
        List<String> permissions = sysMenuDao.findPermission(menuIds.toArray(array));
        if(permissions==null||permissions.size()==0){
            throw new AuthenticationException();
        }
        //5.封装结果并返回
        Set<String> stringPermissions = new HashSet<>();
        for (String permission:permissions) {
            if(!StringUtils.isEmpty(permission)){
                stringPermissions.add(permission);
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(stringPermissions);
        return info;
    }

    /**获取用户认证信息并封装返回*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.获取用户提交的身份信息
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        //2.基于身份信息查询数据库中的用户
        SysUser user = sysUserDao.findUserByUserName(username);
        //3.判定用户是否存在
        if(user==null){
            throw new UnknownAccountException();
        }
        //4.判定用户是否被禁用
        if(user.getValid()==0){
            throw new LockedAccountException();
        }
        //5.封装用户信息并返回
        /**
         * 这里使用AuthenticationInfo的实现类：SimpleAuthenticationInfo来封装用户数据
         * SimpleAuthenticationInfo使用构造方法封装数据，它的构造方法有四个参数：
         *      1、principal：身份（用户对象）
         *      2、hashedCredentials：已加密的凭证
         *      3、credentialsSalt：加密时使用的盐，需要用ByteSource类型处理一下
         *      4、realmName：使用的realm的名字
         *
         */
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, getName());
        return info;//返回给SecurityManager（后续认证管理器会通过此信息完成认证操作）
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        //构建凭证匹配对象
        HashedCredentialsMatcher cMatcher = new HashedCredentialsMatcher();
        //设置加密算法
        cMatcher.setHashAlgorithmName("MD5");
        //设置加密次数
        cMatcher.setHashIterations(1);
        super.setCredentialsMatcher(cMatcher);
    }
}
