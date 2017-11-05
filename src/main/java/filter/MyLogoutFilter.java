package filter;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Service
public class MyLogoutFilter extends LogoutFilter {
    private static final Logger log = LoggerFactory.getLogger(MyLogoutFilter.class);

    public MyLogoutFilter() {
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        // 在这里执行退出系统前需要清空的数据
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        try {
            subject.logout();
        } catch (SessionException ise) {
            ise.printStackTrace();
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        issueRedirect(request, response, redirectUrl);
        // 返回 false 表示不执行后续的过滤器，直接返回跳转到登录页面
        return false;
    }
}
// http://www.cnblogs.com/nosqlcoco/p/5587294.html
// http://blog.csdn.net/chengxuzaza/article/details/72851707