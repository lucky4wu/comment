package accew.comment.web;

import accew.comment.model.Comment;
import org.json.JSONML;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by CrazyFive on 2017/2/28.
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @ResponseBody
    @RequestMapping(value = "/listPage", method = RequestMethod.GET)
    public String listPage(HttpServletRequest request, Model model){
        List<Comment> commentList = new ArrayList<Comment>();
        for (int i=0;i<10; i++){
            Comment comment = new Comment();
            comment.setComment("我的评论"+i);
            comment.setCreateUser("acc");
            comment.setCreateTime(Calendar.getInstance().getTime());
            commentList.add(comment);
        }
        return JSONObject.valueToString(commentList);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model){

        return "/comment/list";
    }
}
