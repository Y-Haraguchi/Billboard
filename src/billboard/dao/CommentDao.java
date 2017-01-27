package billboard.dao;

import static billboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import billboard.beans.Comment;
import billboard.beans.UserComment;
import billboard.exception.SQLRuntimeException;

public class CommentDao {

	public void insert(Connection connection, Comment comment) {

		PreparedStatement ps = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments (");
			sql.append("messages_id");
			sql.append(", body");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(", user_id");
			sql.append(") VALUES (");
			sql.append("?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", ?");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, comment.getMessage_id());
			ps.setString(2, comment.getBody());
			ps.setInt(3, comment.getUser_id());

			ps.executeUpdate();

		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}


	}
	public void delete(Connection connection, UserComment userCcomment) {

		PreparedStatement ps = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM comments WHERE id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, userCcomment.getId());

			ps.executeUpdate();

		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}


	}

}
