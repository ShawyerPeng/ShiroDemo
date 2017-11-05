package exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义ExceptionHandler
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        System.out.println("---------------i am resolveException");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ex", e);

        ModelAndView modelAndView = new ModelAndView();
        // 根据不同错误转向不同页面
        if(e instanceof IOException) {
            modelAndView.addObject("message", "IOException");
            modelAndView.setViewName("error/error");
        }
        else if(e instanceof SQLException) {
            modelAndView.addObject("message", "SQLException");
            modelAndView.setViewName("error/error");
        } else {
            e.getMessage();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.setViewName("error/error");
        }
        return modelAndView;
    }
}
