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

	public List<UserComment> getUserComments(Connection connection, Integer userId, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM billboard.users_messages ");

			if(userId != null) {
				sql.append("WHERE user_id = ?");
			}
			sql.append(" ORDER BY insert_date DESC limit " + num);
			ps = connection.prepareStatement(sql.toString());
			if(userId != null) {
				ps.setInt(1, userId);
			}
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
				UserComment comment = new UserComment();
				comment.setId(rs.getInt("id"));
				comment.setName(rs.getString("name"));
				comment.setBody(rs.getString("body"));
				comment.setInsertDate(rs.getTimestamp("insert_date"));
				comment.setUpdateDate(rs.getTimestamp("update_date"));
				comment.setUser_id(rs.getInt("user_id"));

				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}


	}

}
