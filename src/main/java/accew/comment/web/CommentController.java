package accew.comment.web;

import accew.comment.model.Comment;
import accew.comment.service.CommentService;
import accew.common.BaseController;
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
public class CommentController extends BaseController{

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
        String userNo = getUserNo(request, response);
        comment.setCreateUser(userNo);

        commentService.addComment(comment);
        return "/comment/list";
    }



}
