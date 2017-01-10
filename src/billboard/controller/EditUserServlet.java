package billboard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import billboard.beans.AssignType;
import billboard.beans.Branch;
import billboard.beans.User;
import billboard.service.AssignTypeService;
import billboard.service.BranchService;
import billboard.service.UserService;

@WebServlet(urlPatterns = { "/editUser" })
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		int editUserId = Integer.parseInt(request.getParameter("user_id"));

		User editUser = new UserService().getEditUser(editUserId);
		List<Branch> branches = new BranchService().getBranches();
		List<AssignType> assignTypes = new AssignTypeService().getAssignTypes();
		request.setAttribute("editUser", editUser);
		request.setAttribute("branchList", branches);
		request.setAttribute("assignTypeList", assignTypes);
		request.getRequestDispatcher("/editUser.jsp").forward(request, response);
	}

}
