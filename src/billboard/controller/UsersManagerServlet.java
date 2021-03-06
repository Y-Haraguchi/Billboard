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
import billboard.service.UserManagerService;
import billboard.service.UserService;

@WebServlet(urlPatterns = { "/usersManager" })
public class UsersManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		List<User> users = new UserService().getUserList();
		List<Branch> branches = new BranchService().getBranches();
		List<AssignType> assignTypes = new AssignTypeService().getAssignTypes();
		session.setAttribute("loginUsers", loginUser);
		session.setAttribute("branchList", branches);
		session.setAttribute("assignTypeList", assignTypes);
		session.setAttribute("enteredUsers", users);

		request.getRequestDispatcher("/usersManager.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		int isBanUserId = Integer.parseInt(request.getParameter("user_id"));
		int isBan = Integer.parseInt(request.getParameter("isBan"));

		User isBanUser = new UserService().getEditUser(isBanUserId);

		isBanUser.setId(isBanUserId);
		isBanUser.setIsBan(isBan);

		new UserManagerService().isBanChange(isBanUser);
		session.setAttribute("isBanUser", isBanUser);
		response.sendRedirect("usersManager");

	}


}
