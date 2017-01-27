package billboard.dao;

import static billboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import billboard.beans.UserMessage;
import billboard.exception.SQLRuntimeException;

public class UserMessageDao {

	public Timestamp getMinDate(Connection connection) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT MIN(insert_date) AS start_date FROM users_messages";

			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			rs.next();
			return rs.getTimestamp("start_date");

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<UserMessage> getNallowUserMessages(Connection connection, String category, String startDate, String endDate, int num) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_messages ");
			sql.append("WHERE category LIKE ? ");
			sql.append(" AND insert_date BETWEEN ? AND ?");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, "%" +  category + "%");
			System.out.println(category);
			ps.setString(2, startDate);
			System.out.println();
			ps.setString(3, endDate + " 23:59:59");

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
				userMessage.setBranch_id(rs.getInt("branch_id"));
				userMessage.setAssign_type_id(rs.getInt("assign_type_id"));
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
