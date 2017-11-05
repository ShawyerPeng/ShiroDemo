//package filter;
//
//import model.User;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
//import org.apache.shiro.web.util.WebUtils;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//
//public class JWTFilter extends AuthenticatingFilter {
//    @Override
//    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
//        String token = getToken(request);
//        try {
//            User user = JWT.verifyToken(token);
//            return createToken(user.getUsername(), user.getPassword(), request, response);
//        } catch (Exception e) {
//            throw new AuthenticationException(e);
//        }
//    }
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        if ("OPTIONS".equals(WebUtils.toHttp(request).getMethod())) {
//            return true;
//        }
//        Subject subject = getSubject(request, response);
//        return subject.isAuthenticated();
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest req, ServletResponse rsp, Object mappedValue) throws Exception {
//        boolean allowThru = onAccessDenied(req, rsp) || isPermissive(mappedValue);
//        if (!allowThru) {
//            WebUtils.toHttp(rsp).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }
//        return allowThru;
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest req, ServletResponse rsp) throws Exception {
//        try {
//            return executeLogin(req, rsp);
//        } catch (Exception e) {
//            Logger.getLogger(JWTFilter.class.getName()).log(Level.ALL, null, e);
//            return false;
//        }
//    }
//
//    /**
//     * Searches through all the cookies in the httpRequest for a cookie containing a JWT Token.
//     * The name of the cookie to look for is configured in the JWT Class.
//     */
//    private String getToken(ServletRequest httpRequest) {
//        Cookie[] cookies = WebUtils.toHttp(httpRequest).getCookies();
//
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(JWT.cookieName)) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null;
//    }
//}
//// http://dysania.cn/2017/05/26/shiro-JWT/
//// http://www.mekau.com/4430.html
//// https://github.com/recursosCSWuniandes/auth-shiro-stormpath/blob/master/src/main/java/co/edu/uniandes/csw/auth/filter/JWTFilter.java