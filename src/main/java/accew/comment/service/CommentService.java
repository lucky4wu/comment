package accew.comment.service;

import accew.comment.dao.CommentDao;
import accew.comment.model.Comment;
import accew.common.enums.CommentStatus;
import accew.common.enums.IsYn;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
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
        if (StringUtils.isNotEmpty(comment.getImageUrl())){
            comment.setStatus(CommentStatus.init.getValue());
        } else {
            comment.setStatus(CommentStatus.checked.getValue());
        }
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
        childComment.setTitle("");
        childComment.setType("01");
        childComment.setImageUrl(comment.getImageUrl());
        if (StringUtils.isNotEmpty(childComment.getImageUrl())){
            childComment.setStatus(CommentStatus.init.getValue());
        }else {
            childComment.setStatus(CommentStatus.checked.getValue());
        }
        childComment.setYn("Y");
        childComment.setCreateUser(userNo);
        childComment.setCreateTime(Calendar.getInstance().getTime());
        childComment.setVersions(0L);

        commentDao.insert(childComment);
    }

    public List<Comment> queryPage(Comment comment) {
        comment.setType("03");
        comment.setYn(IsYn.YES.getValue());
        comment.setStatus(CommentStatus.checked.getValue());
        return commentDao.selectList(comment);
    }

    public List<Comment> queryPage(Comment comment, int pageNum, int pageSize){
        comment.setYn("Y");
        return commentDao.selectList(comment);
    }

    public List<Comment> queryParentTitleAndContent(Long id) {
        Comment parentTitle = commentDao.selectByPrimaryKey(id);

        Comment comment = new Comment();
        comment.setParentId(parentTitle.getId());
        comment.setYn("Y");
        comment.setStatus(CommentStatus.checked.getValue());
        List<Comment> commentList = commentDao.selectListByParentId(comment);
        if (commentList != null && commentList.size() > 0){
            commentList.add(0, parentTitle);
        }else {
            commentList = new ArrayList<Comment>();
            commentList.add(parentTitle);
        }
        return commentList;
    }

    @Transactional(value = "transactionManager", readOnly = false)
    public void check(Comment comment, String userNo) {
        Comment commentDb = commentDao.selectByPrimaryKey(comment.getId());
        comment.setCheckTime(Calendar.getInstance().getTime());
        comment.setCheckUser(userNo);
        comment.setStatus(CommentStatus.checked.getValue());
        commentDao.updateByPrimaryKeySelective(comment);
    }

    @Transactional(value = "transactionManager", readOnly = false)
    public void delete(Comment comment, String userNo) {
        comment.setCheckTime(Calendar.getInstance().getTime());
        comment.setCheckUser(userNo);
        comment.setYn("N");
        commentDao.updateByPrimaryKeySelective(comment);
    }
}
