package billboard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import billboard.beans.AssignType;
import billboard.beans.Branch;
import billboard.beans.User;
import billboard.service.AssignTypeService;
import billboard.service.BranchService;
import billboard.service.EditUserService;
import billboard.service.UserService;

@WebServlet(urlPatterns = { "/editUser" })
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		int editUserId = Integer.parseInt(request.getParameter("user_id"));

		User editUser = new UserService().getEditUser(editUserId);
		List<Branch> branches = new BranchService().getBranches();
		List<AssignType> assignTypes = new AssignTypeService().getAssignTypes();
		session.setAttribute("editUser", editUser);
		session.setAttribute("branchList", branches);
		session.setAttribute("assignTypeList", assignTypes);
		request.getRequestDispatcher("/editUser.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		User editUser = (User)session.getAttribute("editUser");
		editUser.setLoginId(request.getParameter("login_id"));
		editUser.setPassword(request.getParameter("password"));
		editUser.setName(request.getParameter("name"));
		editUser.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
		editUser.setAssignTypeId(Integer.parseInt(request.getParameter("assign_type_id")));

		new EditUserService().update(editUser);
		session.setAttribute("editUser", editUser);
		response.sendRedirect("usersManager");
	}

}
