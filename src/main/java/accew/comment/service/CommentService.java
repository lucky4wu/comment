package accew.comment.service;

import accew.comment.dao.CommentDao;
import accew.comment.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

/**
 * Created by acc on 2017/3/3.
 */
@Service
@Transactional(value = "transactionManager", readOnly = true)
public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Transactional(value = "transactionManager", readOnly = false)
    public void addComment(Comment comment){
        comment.setParentId(0L);
        comment.setType("03");
        comment.setStatus("01");
        comment.setYn("Y");
        comment.setCreateTime(Calendar.getInstance().getTime());
        comment.setVersions(0L);

        commentDao.insert(comment);
    }

    @Transactional(value = "transactionManager", readOnly = false)
    public void replyContent(Comment comment, String userNo){
        Comment parentComment= commentDao.selectByPrimaryKey(comment.getId());
        parentComment.setTs(Calendar.getInstance().getTime());
        parentComment.setUpdateUser(userNo);
        commentDao.updateByPrimaryKeySelective(parentComment);

        Comment childComment = new Comment();
        childComment.setParentId(comment.getId());
        childComment.setComment(comment.getComment());
        childComment.setType("01");
        childComment.setStatus("01");
        childComment.setYn("Y");
        childComment.setCreateUser(userNo);
        childComment.setCreateTime(Calendar.getInstance().getTime());
        childComment.setVersions(0L);

        commentDao.insert(childComment);
    }

    public List<Comment> queryPage() {
        Comment comment = new Comment();
        return commentDao.selectList(comment);
    }
}
