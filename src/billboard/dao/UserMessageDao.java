package billboard.dao;

import static billboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import billboard.beans.UserMessage;
import billboard.exception.SQLRuntimeException;

public class UserMessageDao {

	public List<UserMessage> getUserMessages(Connection connection, Integer userId, int num) {

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
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	private List<UserMessage> toUserMessageList(ResultSet rs) throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while(rs.next()) {
				UserMessage userMessage = new UserMessage();
				userMessage.setMessage_id(rs.getInt("messages_id"));
				userMessage.setId(rs.getInt("id"));
				userMessage.setName(rs.getString("name"));
				userMessage.setTitle(rs.getString("title"));
				userMessage.setCategory(rs.getString("category"));
				userMessage.setBody(rs.getString("body"));
				userMessage.setInsertDate(rs.getTimestamp("insert_date"));
				userMessage.setUpdateDate(rs.getTimestamp("update_date"));
				userMessage.setUser_id(rs.getInt("user_id"));

				ret.add(userMessage);
			}
			return ret;
		} finally {
			close(rs);
		}

	}

}
