package accew.comment.web;

import accew.comment.model.Comment;
import accew.comment.service.CommentService;
import accew.common.BaseController;
import accew.common.MessageResult;
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


    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public String addComment(Comment comment, HttpServletRequest request, HttpServletResponse response, Model model){
        String userNo = getUserNo(request, response);
        comment.setCreateUser(userNo);

        commentService.addComment(comment);

        model.addAttribute("userNo", userNo);

        return "../../index";
    }

    @ResponseBody
    @RequestMapping(value = "/replyContent/{id}", method = RequestMethod.POST)
    public String replyContent(@PathVariable Long id, Comment comment, HttpServletRequest request, HttpServletResponse response){
        String userNo = getUserNo(request, response);
        comment.setId(id);
        MessageResult mr;
        try {
            commentService.replyContent(comment, userNo);
            mr = getSuccessMsg();
        }catch (Exception e){
            e.printStackTrace();
            mr = getCodeAndMsg(999, e.getMessage());
        }
        return JSONObject.valueToString(mr);
    }

    @ResponseBody
    @RequestMapping(value = "/getParentTitleAndContent/{id}", method = RequestMethod.GET)
    public String getParentTitleAndContent(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response){
        List<Comment> commentList = commentService.queryParentTitleAndContent(id);

        return JSONObject.valueToString(commentList);
    }

}
