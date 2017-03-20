package accew.comment.dao;

import accew.comment.model.Comment;
import accew.modules.mybatis.BaseMyBatisDao;
import accew.modules.mybatis.MyBatisDao;

import java.util.List;

/**
 * Created by acc on 2017/3/3.
 */
@MyBatisDao
public interface CommentDao extends BaseMyBatisDao<Comment, Long>{

    List<Comment> selectListByParentId(Comment comment);
}

