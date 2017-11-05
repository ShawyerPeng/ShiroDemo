package filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义 RolesAuthorizationFilter
 */
public class MyRoleFilter extends RolesAuthorizationFilter {
    private Logger log = LoggerFactory.getLogger(MyRoleFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest req = WebUtils.toHttp(request);
        HttpServletResponse res = WebUtils.toHttp(response);
        String path = WebUtils.getRequestUri(req);
        //my开头的请求用于页面跳转
        if (path.startsWith("/my")) {
            res.sendError(401);
        } else { //其余是ajax异步请求
            PrintWriter out = response.getWriter();
            out.println("{\"error_info\":\"permission denied.\"}");
            out.flush();
            out.close();
        }
        return false;
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        final Subject subject = getSubject(request, response);
        HttpServletRequest req = (HttpServletRequest) request;
        //mappedValue的值就是上面spring-shiro.xml 过滤器链定义中roles["user,admin"] 括号里面的值
        final String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for (String roleName : rolesArray) {
            if (subject.hasRole(roleName)) {  //判断当前用户是否拥有这个角色
                return true;
            }
        }
        return false;
    }
}
