package accew.comment.web;

import accew.comment.model.Comment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by CrazyFive on 2017/2/28.
 */
@RestController("comment")
public class CommentController {

    @ResponseBody
    @RequestMapping(value = "listPage", method = RequestMethod.GET)
    public List<Comment> listPage(){
        List<Comment> commentList = new ArrayList<Comment>();
        for (int i=0;i<10; i++){
            Comment comment = new Comment();
            comment.setComment("我的评论"+i);
            comment.setCreateUser("acc");
            comment.setCreateTime(Calendar.getInstance().getTime());
            commentList.add(comment);
        }
        return commentList;
    }

    @RequestMapping(value = "list")
    public String list(){
        return "/comment/list";
    }
}
