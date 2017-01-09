package billboard.service;

import static billboard.utils.CloseableUtil.*;
import static billboard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import billboard.beans.Message;
import billboard.beans.UserMessage;
import billboard.dao.MessageDao;
import billboard.dao.UserMessageDao;

public class NewMessageService {

	private static final int LIMIT_NUM = 1000;

	public void register(Message message) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			messageDao.insert(connection, message);

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
	public List<UserMessage> getMessage(Integer user_id) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			List<UserMessage> ret = messageDao.getUserMessages(connection, user_id, LIMIT_NUM);

			commit(connection);

			return ret;

		} catch(RuntimeException e) {
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