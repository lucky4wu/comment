package accew.admin.web;

import accew.comment.model.User;
import accew.common.BaseController;
import accew.common.constant.SysConstants;
import accew.modules.utils.LoginSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by acc on 2017/3/9.
 */
@Controller
@RequestMapping("in")
public class LoginController extends BaseController {

    @RequestMapping("loginForm")
    public String loginForm(){
        return "/in/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String doLogin(User user, HttpServletRequest request, HttpServletResponse response, Model model){
        LoginSession loginSession = (LoginSession) request.getSession().getAttribute(SysConstants.LOGIN_ADMIN_SESSION);
        if (loginSession != null) {
            return "/admin/commentMgr";
        }
        if ("admin".equals(user.getName()) && "1234".equals(user.getPassword())){
            loginSession = new LoginSession();
            loginSession.setUserNo(getUserNo(request, response));
            request.getSession().setAttribute(SysConstants.LOGIN_ADMIN_SESSION, loginSession);
            try {
                response.sendRedirect("/admin/commentMgr");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "/admin/commentMgr";
        }else {
            model.addAttribute("errorMsg", "用户名或密码错误!");
            return "/in/login";
        }
    }
}
