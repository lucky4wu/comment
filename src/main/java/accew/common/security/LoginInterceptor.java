package accew.common.security;

import accew.common.constant.SysConstants;
import accew.modules.security.AntUrlPathMatcher;
import accew.modules.security.UrlMatcher;
import accew.modules.utils.LoginSession;
import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by acc on 2017/3/9.
 */
public class LoginInterceptor implements HandlerInterceptor {

    private String include;

    private String exclude;

    private UrlMatcher urlMatcher = new AntUrlPathMatcher();

    private static Logger logger = accew.modules.logger.Logger.getLog(LoginInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String servletPath = request.getServletPath();
        if (checkAction(servletPath)){
            return true;
        }
        LoginSession loginSession = (LoginSession) request.getSession().getAttribute(SysConstants.LOGIN_ADMIN_SESSION);
        if (loginSession == null){
            response.sendRedirect("/in/loginForm");
            return false;
        }else {
            return true;
        }
    }

    /**
     * checkAction:(檢查是否符合包含的情形)
     *
     * @param uri
     * @return (true=可以通过, false=需要檢查)
     */
    private boolean checkAction(String uri) {
        boolean result = true;
        String[] includeStr = new String[0];
        if (include != null && include.length() > 0){
            includeStr = include.split(",");
        }
        for (String method : includeStr) {
            if (urlMatcher.pathMatchesUri(method, uri)){
                result = false;
                break;
            }
        }

        return result;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        if (include != null) {
            this.include = include.replaceAll("\\s*", "");
        }
        else {
            this.include = include;
        }
    }


}
