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
				UserMessage message = new UserMessage();
				message.setId(rs.getInt("id"));
				message.setName(rs.getString("name"));
				message.setTitle(rs.getString("title"));
				message.setCategory(rs.getString("category"));
				message.setBody(rs.getString("body"));
				message.setInsertDate(rs.getTimestamp("insert_date"));
				message.setUpdateDate(rs.getTimestamp("update_date"));
				message.setUser_id(rs.getInt("user_id"));

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}

	}

}
