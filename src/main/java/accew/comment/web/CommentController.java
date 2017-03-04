package accew.comment.web;

import accew.comment.model.Comment;
import accew.comment.service.CommentService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by CrazyFive on 2017/2/28.
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    private static Logger logger = accew.modules.logger.Logger.getLog(CommentController.class);

    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public String listPage(HttpServletRequest request, Model model){
        List<Comment> commentList = commentService.queryPage();

        return JSONObject.valueToString(commentList);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model){

        return "../../index";
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(Comment comment, HttpServletRequest request, HttpServletResponse response){
        String jSessionId = "";
        String sessionId = request.getSession().getId();
        if (request.getCookies() != null && request.getCookies().length > 0){
            boolean flag = true;
            for (Cookie cookie : request.getCookies()){
                if (sessionId.equals(cookie.getName())){
                    comment.setCreateUser(cookie.getValue());
                    flag = false;
                    break;
                }
            }
            if (flag){
                for (Cookie cookie : request.getCookies()){
                    if ("JSESSIONID".equals(cookie.getName())){
                        jSessionId = cookie.getValue();
                        break;
                    }
                }
                if (jSessionId.equals(sessionId) || StringUtils.isEmpty(jSessionId)){
                    Cookie newCookie = new Cookie(sessionId, "acc"+ new Random(100).nextInt());
                    newCookie.setMaxAge(3600*24);
                    response.addCookie(newCookie);
                    comment.setCreateUser(newCookie.getValue());
                }
            }

        }else {
            comment.setCreateUser("no cookie");
        }

        commentService.addComment(comment);
        return "/comment/list";
    }
}
