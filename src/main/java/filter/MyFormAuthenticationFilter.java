package filter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 进行表单验证。需要在配置文件中进行这个类的配置
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        /* System.out.println("----------- i am MyFormAuthenticationFilter");
        // 校验验证码
        // 从 session 获取正确的验证码
        HttpSession session = ((HttpServletRequest)request).getSession();
        // 页面输入的验证码
        String randomcode = request.getParameter("randomcode");
        // 从 session 中取出验证码
        String validateCode = (String) session.getAttribute("validateCode");
        System.out.println("----randomcode:"+randomcode);
        System.out.println("---validateCode:"+validateCode);
       /* if (randomcode!=null&&validateCode!=null&&!randomcode.equals(validateCode)) {
            // randomCodeError 表示验证码错误
            request.setAttribute("shiroLoginFailure", "randomCodeError");
            // 拒绝访问，不再校验账号和密码
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
        */
        return true;
    }

    /**
     * 认证
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = createToken(request, response);
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    /**
     * 创建 Token
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        System.out.println("------ I'm MyFormAuthenticationFilter ------");
        String username = getUsername(request);
        String password = getPassword(request);
        //String captcha = getCaptcha(request);
        String rememberMeParam = getRememberMeParam();
        String host = getHost(request);
        System.out.println("----------:" + rememberMeParam);
        if (password == null) {
            password = "";
        }
        boolean rememberMe = true;
        if (rememberMeParam == null || rememberMeParam.equals("0")) {
            rememberMe = false;
        }
        return new UsernamePasswordToken(username, password, rememberMe, host);
    }
}
