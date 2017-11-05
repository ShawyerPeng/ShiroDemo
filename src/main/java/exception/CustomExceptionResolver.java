package exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ExceptionResolver
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 输出异常
        ex.printStackTrace();
        // 统一异常处理
        String message = null;
        CustomException customException = null;
        if (ex instanceof CustomException) {
            // 统一异常: 测试需全部测出
            customException = (CustomException) ex;
            message = customException.getMessage();
        } else {
            // 未知异常: 测试需全部测出
            customException = new CustomException(" 恭喜你，你可以领奖，你发现这个漏洞, 联系客服：400");
        }
        request.setAttribute("message", message);
        try {
            // 错误页面
            request.getRequestDispatcher("/pages/jsp/error.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView();
    }
}
