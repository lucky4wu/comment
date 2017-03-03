package accew.comment.service;

import accew.comment.dao.CommentDao;
import accew.comment.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * Created by acc on 2017/3/3.
 */
@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    public void addComment(Comment comment){
        comment.setParentId(0L);
        comment.setType("03");
        comment.setStatus("01");
        comment.setYn("Y");
        comment.setCreateTime(Calendar.getInstance().getTime());
        comment.setVersions(0);

        commentDao.insert(comment);
    }

    public void replyContent(Comment comment){
        Comment parentComment= commentDao.selectByPrimaryKey(comment.getParentId());
        parentComment.setTs(Calendar.getInstance().getTime());
        commentDao.updateByPrimaryKeySelective(parentComment);

        comment.setParentId(comment.getParentId());
        comment.setType("01");
        comment.setStatus("01");
        comment.setCreateTime(Calendar.getInstance().getTime());

        commentDao.insert(comment);
    }

    public List<Comment> queryPage() {
        Comment comment = new Comment();
        return commentDao.selectList(comment);
    }
}
