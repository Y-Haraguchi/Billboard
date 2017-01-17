package billboard.dao;

import static billboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import billboard.beans.AssignType;
import billboard.exception.SQLRuntimeException;

public class AssignTypeDao {
	public List<AssignType> getAssignTypes(Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM billboard.assign_types ";

			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<AssignType> assignTypeList = toAssignTypeList(rs);

			return assignTypeList;

		} catch(SQLException e) {
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}

	public List<AssignType> toAssignTypeList(ResultSet rs) throws SQLException {

		List<AssignType> ret = new ArrayList<AssignType>();
		try {
			while(rs.next()) {
				AssignType assignType = new AssignType();
				assignType.setId(rs.getInt("id"));
				assignType.setType_name(rs.getString("name"));

				ret.add(assignType);
			}
			return ret;
		} finally {
			close(rs);
		}
	}



}
