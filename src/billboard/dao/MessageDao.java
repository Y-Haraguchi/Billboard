package billboard.dao;

import static billboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import billboard.beans.Message;
import billboard.exception.SQLRuntimeException;

public class MessageDao {

	public void insert(Connection connection, Message message) {

		PreparedStatement ps = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO billboard.messages ( ");
			sql.append("category");
			sql.append(", title");
			sql.append(", body");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(", user_id");
			sql.append(") VALUES (");
			sql.append("?");					//category
			sql.append(", ?");					//title
			sql.append(", ?"); 					//body
			sql.append(", CURRENT_TIMESTAMP");	// insert_date
			sql.append(", CURRENT_TIMESTAMP");	// update_date
			sql.append(", ?");					//user_id
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, message.getCategory());
			ps.setString(2, message.getTitle());
			ps.setString(3, message.getBody());
			ps.setInt(4, message.getUser_id());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

}
