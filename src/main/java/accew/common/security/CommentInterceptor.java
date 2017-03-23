package accew.common.security;

import accew.modules.security.AntUrlPathMatcher;
import accew.modules.security.SensitiveWordFilter;
import accew.modules.security.UrlMatcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by acc on 2017/3/23.
 */
public class CommentInterceptor extends BaseInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String servletPath = request.getServletPath();
        if (checkAction(servletPath)){
            return true;
        }
        String comment = request.getParameter("comment");
        if (StringUtils.isNotEmpty(comment)){
            if (SensitiveWordFilter.isContainSensitiveWord(comment, 0)){

            }
            comment = SensitiveWordFilter.replaceSensitiveWord(comment, 0);
            request.setAttribute("comment", comment);
        }
        return true;
    }


    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
