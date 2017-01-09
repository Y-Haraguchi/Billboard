package billboard.dao;

import static billboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import billboard.beans.User;
import billboard.exception.SQLRuntimeException;


public class UserDao {
	public User getUser(Connection connection, String login_id, String password) {


		PreparedStatement ps = null;
		try {
			//すでに登録されているusersのログインIDとパスワードを参照するsql文
			String sql = "SELECT * FROM billboard.users WHERE login_id = ? AND password = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, login_id);
			ps.setString(2, password);

			//SQLの実行
			ResultSet rs = ps.executeQuery();

			//セットされたデータをリストに格納
			List<User> userList = toUserList(rs);

			//リストの中身が空の場合nullを返す
			if(userList.isEmpty()) {
				return null;
			} else if(userList.size() >= 2) {
				throw new IllegalStateException("userList.size() >= 2");
			} else {
				return userList.get(0);
			}

		} catch(SQLException e) {
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}

	}
	public User getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM billboard.user WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//SQL実行後に各データを格納するためのList型のメソッドを作成
	public List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setLoginId(rs.getString("login_id"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBranchId(rs.getInt("branch_id"));
				user.setAssignTypeId(rs.getInt("assign_type_id"));
				user.setIsBan(rs.getInt("is_ban"));
				user.setInsertDate(rs.getTimestamp("insert_date"));
				user.setUpdateDate(rs.getTimestamp("update_date"));

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}
	public List<User> getUsersList(Connection connection, Integer userId, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM billboard.users ");

			if(userId != null) {
				sql.append("WHERE id = ?");
			}
			sql.append(" ORDER BY insert_date DESC limit " + num);
			ps = connection.prepareStatement(sql.toString());

			if(userId != null) {
				ps.setInt(1, userId);
			}

			ResultSet rs = ps.executeQuery();
			List<User> ret = toUsersList(rs);
			return ret;

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	public List<User> toUsersList(ResultSet rs) throws SQLException {
		List<User> ret = new ArrayList<User>();
		try {
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setLoginId(rs.getString("login_id"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setBranchId(rs.getInt("branch_id"));
				user.setAssignTypeId(rs.getInt("assign_type_id"));
				user.setIsBan(rs.getInt("is_ban"));
				user.setInsertDate(rs.getTimestamp("insert_date"));
				user.setUpdateDate(rs.getTimestamp("update_date"));

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}

	}

}
