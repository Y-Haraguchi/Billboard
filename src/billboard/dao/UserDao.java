package billboard.dao;

import static billboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import billboard.beans.User;
import billboard.exception.SQLRuntimeException;



public class UserDao {
	public User getUser(Connection connection, String loginId, String password) {

		PreparedStatement ps = null;
		try {
			//すでに登録されているusersのログインIDとパスワードを参照するsql文
			String sql = "SELECT * FROM billboard.users WHERE login_id = ? AND password = ?";

			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			System.out.println(ps);
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

	//SQL実行後に各データを格納するためのList型のメソッドを作成
	public List<User> toUserList(ResultSet rs) throws SQLException {

		List<User> ret = new ArrayList<User>();
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String loginId = rs.getString("loginId");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				int assignTypeId = rs.getInt("assign_type_id");
				int isBan = rs.getInt("is_ban");						//1=true 0=false
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");

				//setterでゲットしたデータをset
				User user = new User();
				user.setId(id);
				user.setLoginId(loginId);
				user.setPassword(password);
				user.setName(name);
				user.setBranchId(branchId);
				user.setAssignTypeId(assignTypeId);
				user.setIsBan(isBan);
				user.setInsertDate(insertDate);
				user.setUpdateDate(updateDate);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
