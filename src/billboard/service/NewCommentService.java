package billboard.service;

import static billboard.utils.CloseableUtil.*;
import static billboard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import billboard.beans.Comment;
import billboard.beans.UserComment;
import billboard.dao.CommentDao;
import billboard.dao.UserCommentDao;

public class NewCommentService {

	private static final int LIMIT_NUM = 1000;

	public void register(Comment comment) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.insert(connection, comment);

			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
	public void deleteComment(UserComment comment) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.delete(connection, comment);

			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<UserComment> getComment() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserCommentDao userCommentDao = new UserCommentDao();
			List<UserComment> ret = userCommentDao.getUserComments(connection, LIMIT_NUM);

			commit(connection);
			return ret;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}



	}

}
