package billboard.service;

import static billboard.utils.CloseableUtil.*;
import static billboard.utils.DBUtil.*;

import java.sql.Connection;

import org.apache.commons.lang.StringUtils;

import billboard.beans.User;
import billboard.dao.UserDao;
import billboard.utils.CipherUtil;

public class EditUserService {

	public void update(User editUser, User loginUser) {
		Connection connection = null;
		try {
			connection = getConnection();

			if(!StringUtils.isEmpty(editUser.getPassword())) {
				String encPassword = CipherUtil.encrypt(editUser.getPassword());
				editUser.setPassword(encPassword);
			}

			UserDao userDao = new UserDao();
			//
			if(editUser.getAssignTypeId() == 1) {
				userDao.updateAdministrator(connection, editUser);
			} else {
				userDao.update(connection, editUser);
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
