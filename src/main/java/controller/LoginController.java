package controller;

import model.User;
import model.UserRole;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.RoleService;
import service.UserExtendService;
import service.UserRoleService;
import service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping()
public class LoginController {
    private static transient final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserExtendService userExtendService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 跳转首页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String toIndex(HttpSession session) {
        return "index";
    }

    /**
     * 跳转到注册页面
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registPage(Model model) {
        return "register";
    }

    /**
     * 跳转到后台首页
     */
    @RequestMapping(value = "/admin/index", method = RequestMethod.GET)
    public String cmsHome() {
        return "admin/index";
    }

    /**
     * 访问需要登录的页面时被拦截后，需要跳转到到login页面
     * 结合shiro配置查看
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        log.warn("shiro拦截到需要登录的请求：" + httpServletRequest.getRequestURI());

        Subject subject = SecurityUtils.getSubject();
        boolean loginStatus = subject.isAuthenticated();
        if (loginStatus) {
            log.warn("已登录状态，返回到主页");
            Cookie coo = new Cookie("loginMessage", null);
            httpServletResponse.addCookie(coo);
            return "redirect:index";
        } else {
            log.warn("未登录状态，返回登录页面");
            Cookie cook = new Cookie("loginMessage", null);
            httpServletResponse.addCookie(cook);
            return "login";
        }
    }

    ///**
    // * GET方式：跳转登录页
    // */
    //@RequestMapping(value = "/login", method = RequestMethod.GET)
    //public String login() {
    //    return "login";
    //}

    /**
     * POST方式：业务逻辑
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        System.out.println("username:" + username + ", password:" + password);
        // 权限校验。判断是否包含权限
        Subject currentUser = SecurityUtils.getSubject();

        // 具体响应ShiroDbRealm。doGetAuthorizationInfo，判断是否包含此url的响应权限
        if (!currentUser.isAuthenticated()) {
            // 如果当前用户没有授权，则开始进行登陆验证
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                System.out.println("用户名不正确！");
            } catch (IncorrectCredentialsException ice) {
                System.out.println("密码不匹配！");
            } catch (LockedAccountException lae) {
                System.out.println("账户被锁定!");
            } catch (AuthenticationException ae) {
                System.out.println("认证失败!");
            }
            session.setAttribute("currentUser", currentUser);
        }
        return "/success";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView save(User user) {
        ModelAndView mv = new ModelAndView();
        try {
            userService.insertUser(user);
            Integer id = user.getUserId();
            System.out.println(id);
            /* UserRole userRole = userRoleService.selectByUserId(id); */
            UserRole userRole = new UserRole();
            userRole.setUserId(id);
            // 默认注册用户，RoleId 即为 3
            userRole.setRoleId(3);
            userRoleService.insert(userRole);
            mv.setViewName("success/registSuccess");
        } catch (Exception e) {
            mv.setViewName("error/registError");
            e.printStackTrace();
        }
        mv.addObject("user", userService.selectOne(user.getUserId()));
        return mv;
    }

    ///**
    // * 登出，清除 session
    // */
    //@RequestMapping(value = "/logout")
    //public String logOut(HttpSession session) {
    //    session.removeAttribute("loginUser");
    //    return "redirect:/index";
    //}

    /**
     * ajax 检查用户名是否可用
     */
    @RequestMapping(value = "/user/checkUser", method = RequestMethod.GET)
    @ResponseBody
    public String checkUser(String username, HttpServletRequest resqust, HttpServletResponse response) {
        Boolean b = userService.usernameIsExist(username);
        return b ? "true" : "false";
    }

    /**
     * ajax 验证用户名密码
     */
    @ResponseBody
    @RequestMapping(value = "/user/validatePassword")
    public String validateUser(String username) {
        User _user = userService.selectByUsername(username);
        String _password = _user.getPassword();
        if (_password == null) {
            return "";
        } else {
            return _password;
        }
    }
}
