package shiro;

import model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.*;

import java.util.HashSet;

/**
 * Realm 权限认证
 */
@Component
public class MyRealm extends AuthorizingRealm {
    private static Logger log = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserExtendService userExtendService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;

    // 用于授权(权限认证)。实现对需要登陆之后对权限的认证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        authorizationInfo.setRoles(new HashSet<>(userExtendService.getRoles(username)));    // 角色集合
        authorizationInfo.setStringPermissions(new HashSet<>(userExtendService.getPermissions(username)));  // 用户的权限集合
        return authorizationInfo;

        //User user = userService.selectByUsername(username);
        //Set<String> roleSet = new HashSet<>();
        //List<UserRole> userRoleList = userRoleService.selectByUserId(user.getUserId());
        //for (UserRole userRole : userRoleList) {
        //    roleSet.add(userService.selectOne(userRole.getUserId()).getUsername());
        //}
        //authorizationInfo.setRoles(roleSet);    // 角色集合

        //Set<String> permissionSet = new HashSet<>();
        //List<RolePermission> rolePermissionList = null;
        //for (UserRole userRole : userRoleList) {
        //    rolePermissionList.add(rolePermissionService.selectByRoleId(userRole.getRoleId()));
        //}
        //permissionSet.add(rolePermissionList.g);
    }

    // 用于认证(登录认证)。实现对提交过来的用户名/密码等账号信息，跟数据库进行交互判定登陆是否成功的过程
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("开始登录认证！");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 通过表单接收的用户名
        String username = token.getUsername();
        String password = String.valueOf(token.getPassword());

        if (StringUtils.isNotEmpty(username)) {
            User user = userService.selectByUsername(username);
            if (user == null) {
                log.info(username + "用户名不存在");
                throw new UnknownAccountException("用户名不存在");
            }
            if (!user.getPassword().equals(password)) {
                log.info(username + "密码错误");
                throw new IncorrectCredentialsException("密码错误");
            }
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
            //return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes("admin"), getName());
        }
        return null;
    }

    /**
     * 认证回调函数, 登录时调用
     * 首先根据传入的用户名获取 User 信息；然后如果 user 为空，那么抛出没找到帐号异常 UnknownAccountException；
     * 如果 user 找到但锁定了抛出锁定异常 LockedAccountException；最后生成 AuthenticationInfo 信息，
     * 交给间接父类 AuthenticatingRealm 使用 CredentialsMatcher 进行判断密码是否匹配，
     * 如果不匹配将抛出密码错误异常 IncorrectCredentialsException；
     * 另外如果密码重试此处太多将抛出超出重试次数异常 ExcessiveAttemptsException；
     * 在组装 SimpleAuthenticationInfo 信息时， 需要传入：身份信息（用户名）、凭据（密文密码）、盐（username+salt），
     * CredentialsMatcher 使用盐加密传入的明文密码和此处的密文密码进行匹配。
     */
    //protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    //    String username = (String) token.getPrincipal();
    //
    //    UserFormMap userFormMap = new UserFormMap();
    //    userFormMap.put("accountName", "" + username + "");
    //    List<UserFormMap> userFormMaps = userMapper.findByNames(userFormMap);
    //    if (userFormMaps.size() != 0) {
    //        if ("2".equals(userFormMaps.get(0).get("locked"))) {
    //            throw new LockedAccountException(); // 帐号锁定
    //        }
    //        // 从数据库查询出来的账号名和密码, 与用户输入的账号和密码对比
    //        // 当用户执行登录时, 在方法处理上要实现 user.login(token);
    //        // 然后会自动进入这个类进行认证
    //        // 交给 AuthenticatingRealm 使用 CredentialsMatcher 进行密码匹配，如果觉得人家的不好可以自定义实现
    //        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, // 用户名
    //                userFormMaps.get(0).get("password"), // 密码
    //                ByteSource.Util.bytes(username + "" + userFormMaps.get(0).get("credentialsSalt")),// salt=username+salt
    //                getName() // realm name
    //        );
    //        // 当验证都通过后，把用户信息放在 session 里
    //        Session session = SecurityUtils.getSubject().getSession();
    //        session.setAttribute("userSession", userFormMaps.get(0));
    //        session.setAttribute("userSessionId", userFormMaps.get(0).get("id"));
    //        return authenticationInfo;
    //    } else {
    //        throw new UnknownAccountException();// 没找到帐号
    //    }
    //}
}
// https://github.com/xietf1983/xtcore/blob/master/src/com/lanyuan/shiro/MyRealm.java
