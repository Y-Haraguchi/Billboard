package billboard.dao;

import static billboard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import billboard.beans.Branch;
import billboard.exception.SQLRuntimeException;

public class BranchDao {

	public List<Branch> getBranches(Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM billboard.branches ";

			ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Branch> branchList = toBranchList(rs);

			return branchList;

		} catch(SQLException e) {
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}

	public List<Branch> toBranchList(ResultSet rs) throws SQLException {

		List<Branch> ret = new ArrayList<Branch>();
		try {
			while(rs.next()) {
				Branch branch = new Branch();
				branch.setId(rs.getInt("id"));
				branch.setName(rs.getString("name"));

				ret.add(branch);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
