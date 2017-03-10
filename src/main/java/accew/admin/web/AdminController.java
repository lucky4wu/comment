package accew.admin.web;

import accew.comment.model.Comment;
import accew.comment.model.User;
import accew.comment.service.CommentService;
import accew.common.BaseController;
import accew.common.MessageResult;
import accew.common.constant.SysConstants;
import accew.common.enums.CommentStatus;
import accew.common.enums.SysEnum;
import accew.modules.utils.LoginSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
    public String getCommentList(Comment comment, HttpServletRequest request){
        List<Comment> commentList = commentService.queryPage(comment, 1, 10);
//        MessageResult mr = getSuccessMsg();
//        mr.setInfo(commentList);
        return JSONObject.valueToString(commentList);
    }

    @RequestMapping(value = "/commentMgr", method = RequestMethod.GET)
    public String commentMgrForm(HttpServletRequest request, HttpServletResponse response, Model model){
        List<SysEnum> enumList = new ArrayList<SysEnum>();
        for (CommentStatus status : CommentStatus.values()){
            SysEnum sysEnum = new SysEnum();
            sysEnum.setValue(status.getValue());
            sysEnum.setName(status.getName());
            enumList.add(sysEnum);
        }
        model.addAttribute("statusList", enumList);
        return "/admin/commentMgr";
    }

    @RequestMapping("check/{id}/version/{versions}")
    public String check(@PathVariable Long id, @PathVariable Long versions, HttpServletResponse response)  {
        String userNo = "admin";
        Comment comment = new Comment();
        comment.setId(id);
        comment.setVersions(versions);
        commentService.check(comment, userNo);
        try {
            response.sendRedirect("/admin/commentMgr");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/admin/commentMgr";
    }

    @RequestMapping("delete/{id}/version/{versions}")
    public String delete(@PathVariable Long id, @PathVariable Long versions, HttpServletResponse response)  {
        String userNo = "admin";
        Comment comment = new Comment();
        comment.setId(id);
        comment.setVersions(versions);
        commentService.delete(comment, userNo);
        try {
            response.sendRedirect("/admin/commentMgr");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/admin/commentMgr";
    }

}
