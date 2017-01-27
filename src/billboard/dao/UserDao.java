package billboard.dao;

import static billboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import billboard.beans.User;
import billboard.exception.NoRowsUpdatedRuntimeException;
import billboard.exception.SQLRuntimeException;


public class UserDao {

	public void insert(Connection connection, User user) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append("login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", assign_type_id");
			sql.append(", is_ban");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append("?");				//login_id
			sql.append(", ?");				//password
			sql.append(", ?");				//name
			sql.append(", ?");				//branch_id
			sql.append(", ?");				//assign_type_id
			sql.append(", ?");				//is_ban
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getAssignTypeId());
			ps.setInt(6, user.getIsBan());

			ps.executeUpdate();

		} catch(SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}
	public void update(Connection connection, User editUser) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET ");
			sql.append("login_id = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", assign_type_id = ?");
			sql.append(", update_date = CURRENT_TIMESTAMP");
			//passwordの入力がない場合の処理
			if(!StringUtils.isEmpty(editUser.getPassword())) {
				sql.append(", password = ?");
			}
			sql.append(" WHERE ");
			sql.append("id = ?");
			sql.append(" AND");
			sql.append(" update_date = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, editUser.getLoginId());
			ps.setString(2, editUser.getName());
			ps.setInt(3, editUser.getBranchId());
			ps.setInt(4, editUser.getAssignTypeId());

			if(StringUtils.isEmpty(editUser.getPassword())) {
				ps.setInt(5, editUser.getId());
				ps.setTimestamp(6, new Timestamp(editUser.getUpdateDate().getTime()));
			} else {
				ps.setString(5, editUser.getPassword());
				ps.setInt(6, editUser.getId());
				ps.setTimestamp(7, new Timestamp(editUser.getUpdateDate().getTime()));
			}

			int count = ps.executeUpdate();
			if(count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	public void updateAdministrator(Connection connection, User editUser) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET ");
			sql.append("login_id = ?");
			sql.append(", name = ?");
			sql.append(", update_date = CURRENT_TIMESTAMP ");
			if(!StringUtils.isEmpty(editUser.getPassword())) {
				sql.append(", password = ?");
			}
			sql.append(" WHERE ");
			sql.append("id = ?");
			sql.append(" AND");
			sql.append(" update_date = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, editUser.getLoginId());
			ps.setString(2, editUser.getName());
			if(StringUtils.isEmpty(editUser.getPassword())) {
				ps.setInt(3, editUser.getId());
				ps.setTimestamp(4, new Timestamp(editUser.getUpdateDate().getTime()));
			} else {
				ps.setString(3, editUser.getPassword());
				ps.setInt(4, editUser.getId());
				ps.setTimestamp(5, new Timestamp(editUser.getUpdateDate().getTime()));
			}

			int count = ps.executeUpdate();
			if(count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void isBanUpdate(Connection connection, User user) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET is_ban = ");
			sql.append("CASE");
			sql.append(" WHEN is_ban = 1 THEN 0 ELSE 1 ");
			sql.append("END ");
			sql.append(", update_date = CURRENT_TIMESTAMP ");
			sql.append("WHERE ");
			sql.append("id = ?");
			sql.append(" AND");
			sql.append(" update_date = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, user.getId());
			ps.setTimestamp(2, new Timestamp(user.getUpdateDate().getTime()));

			int count = ps.executeUpdate();

			if(count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}

	public User getUser(Connection connection, String login_id, String password) {

		PreparedStatement ps = null;
		try {
			//すでに登録されているusersのログインIDとパスワードを参照するsql文
			String sql = "SELECT * FROM users WHERE login_id = ? AND password = ?";

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
			String sql = "SELECT * FROM users WHERE id = ?";
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
	public User getUser(Connection connection, String loginId) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE login_id = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);

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
	public List<User> getUsersList(Connection connection, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT * FROM users ");

			sql.append(" ORDER BY insert_date ASC limit " + num);
			ps = connection.prepareStatement(sql.toString());

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
