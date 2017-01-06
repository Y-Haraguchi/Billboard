package billboard.service;

import static billboard.utils.CloseableUtil.*;
import static billboard.utils.DBUtil.*;

import java.sql.Connection;

import billboard.beans.Comment;

public class NewCommentService {

	public void register(Comment comment) {

		Connection connection = null;
		try {
			connection = getConnection();

			//CommentDaoオブジェクト生成



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
