package billboard.dao;

import static billboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import billboard.beans.UserComment;
import billboard.exception.SQLRuntimeException;

public class UserCommentDao {

	public List<UserComment> getUserComments(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM billboard.users_comments");

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			List<UserComment> ret = toUserCommentList(rs);
			return ret;
		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<UserComment> toUserCommentList(ResultSet rs) throws SQLException {

		List<UserComment> ret = new ArrayList<UserComment>();
		try {
			while(rs.next()) {
				UserComment userComment = new UserComment();

				userComment.setMessage_id(rs.getInt("messages_id"));
				userComment.setId(rs.getInt("id"));
				userComment.setName(rs.getString("name"));
				userComment.setBody(rs.getString("body"));
				userComment.setInsertDate(rs.getTimestamp("insert_date"));
				userComment.setUpdateDate(rs.getTimestamp("update_date"));
				userComment.setUser_id(rs.getInt("user_id"));

				ret.add(userComment);
			}
			return ret;
		} finally {
			close(rs);
		}


	}

}
