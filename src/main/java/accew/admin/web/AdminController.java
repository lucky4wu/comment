package accew.admin.web;

import accew.comment.model.Comment;
import accew.comment.model.User;
import accew.comment.service.CommentService;
import accew.common.BaseController;
import accew.common.MessageResult;
import accew.common.constant.SysConstants;
import accew.modules.utils.LoginSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by acc on 2017/3/9.
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/getCommentList", method = RequestMethod.GET)
    public MessageResult getCommentList(Comment comment, HttpServletRequest request){
        List<Comment> commentList = commentService.queryPage(comment, 1, 10);
        MessageResult mr = getSuccessMsg();
        mr.setInfo(commentList);
        return mr;
    }



}
