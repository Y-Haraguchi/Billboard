package billboard.service;

import static billboard.utils.CloseableUtil.*;
import static billboard.utils.DBUtil.*;

import java.sql.Connection;

import billboard.beans.User;
import billboard.dao.UserDao;
import billboard.utils.CipherUtil;

public class EditUserService {

	public void update(User user, User loginUser) {
		Connection connection = null;
		try {
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);
			UserDao userDao = new UserDao();
			if(loginUser.getAssignTypeId() != 1) {
				userDao.update(connection, user);
			} else {
				userDao.updateAdministrator(connection, user);
			}

			commit(connection);

		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}

	}

}
