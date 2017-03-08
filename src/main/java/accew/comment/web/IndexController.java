package accew.comment.web;

import accew.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by acc on 2017/3/8.
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController{

    @ResponseBody
    @RequestMapping("/getUserNo")
    public String index(HttpServletRequest request, HttpServletResponse response){
        String userNo = getUserNo(request, response);

        return userNo;
    }
}
