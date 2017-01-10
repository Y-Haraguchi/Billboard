package billboard.service;

import static billboard.utils.CloseableUtil.*;
import static billboard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import billboard.beans.AssignType;
import billboard.dao.AssignTypeDao;

public class AssignTypeService {

	public List<AssignType> getAssignTypes() {
		Connection connection = null;
		try {
			connection = getConnection();

			AssignTypeDao assignTypeDao = new AssignTypeDao();
			List<AssignType> ret = assignTypeDao.getAssignTypes(connection);

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
