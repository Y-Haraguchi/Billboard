package billboard.service;

import static billboard.utils.CloseableUtil.*;
import static billboard.utils.DBUtil.*;

import java.sql.Connection;

import billboard.beans.User;
import billboard.dao.UserDao;

public class UserManagerService {

	public void isBanChange(User user) {
		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			userDao.isBanUpdate(connection, user);

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
