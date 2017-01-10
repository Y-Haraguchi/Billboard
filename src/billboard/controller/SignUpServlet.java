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
import billboard.service.SignUpService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<Branch> branches = new BranchService().getBranches();
		List<AssignType> assignTypes = new AssignTypeService().getAssignTypes();
		request.setAttribute("branchList", branches);
		request.setAttribute("assignTypeList", assignTypes);
		request.getRequestDispatcher("/signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
//		String password = request.getParameter("password");
//		String checkPassword = request.getParameter("checkPassword");
		System.out.println(request.getParameter("branch_id"));
		int branch_id = Integer.parseInt(request.getParameter("branch_id"));
		int assign_type_id = Integer.parseInt(request.getParameter("assign_type_id"));
//		//仮判定→後にヴァリデーションを挿入
//		if(password != checkPassword) {
//			response.sendRedirect("signup");
//		}
		User user = new User();
		user.setLoginId(request.getParameter("login_id"));
		user.setName(request.getParameter("name"));
		user.setPassword(request.getParameter("password"));
		user.setBranchId(branch_id);
		user.setAssignTypeId(assign_type_id);
		user.setIsBan(1);

		new SignUpService().register(user);
		session.setAttribute("recordUser", user);
		response.sendRedirect("usersManager");
	}

}
