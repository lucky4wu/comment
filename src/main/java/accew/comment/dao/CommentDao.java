package accew.comment.dao;

import accew.comment.model.Comment;
import accew.comment.modules.mybatis.BaseMyBatisDao;
import accew.comment.modules.mybatis.MyBatisDao;

/**
 * Created by acc on 2017/3/3.
 */
@MyBatisDao
public interface CommentDao extends BaseMyBatisDao<Comment, Long>{

}

