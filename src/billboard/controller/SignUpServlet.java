package billboard.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import billboard.service.UserService;

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
		int branch_id = Integer.parseInt(request.getParameter("branch_id"));
		int assign_type_id = Integer.parseInt(request.getParameter("assign_type_id"));
		List<String> messages = new ArrayList<String>();
		User user = new User();

		if(isValid(request, messages)) {
			user.setLoginId(request.getParameter("login_id"));
			user.setName(request.getParameter("name"));
			user.setPassword(request.getParameter("password"));
			user.setBranchId(branch_id);
			user.setAssignTypeId(assign_type_id);
			user.setIsBan(1);

			new SignUpService().register(user);
			session.setAttribute("recordUser", user);
			response.sendRedirect("usersManager");
		} else {
			user.setLoginId(request.getParameter("login_id"));
			user.setName(request.getParameter("name"));
			request.setAttribute("signupUser", user);
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("signup");
		}
	}

	private boolean isValid(HttpServletRequest request,
			List<String> messages) {

		String loginId = request.getParameter("login_id");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkPassword");

		if(loginId.isEmpty()) {
			messages.add("ログインIDを入力してください");
		} else if(6 > loginId.length() || loginId.length() > 20) {
			messages.add("６文字以上２０字以下で入力してください");
		}else if(loginId.matches("\\w")) {
			messages.add("半角英数字で入力してください");
		}

		//重複チェックの為にDBにアクセス
		//ログインIDの重複チェック
		if(new UserService().getUsersLoginId(loginId)) {
			messages.add("ログインIDが重複しています");
		}
		System.out.println(new UserService().getUsersLoginId(loginId));

		if(password.isEmpty()) {
			messages.add("パスワードを入力してください");
		} else if(6 > password.length() || password.length() > 255) {
			messages.add("６文字以上２５５文字以下で入力してください");
		} else if(password.matches("[ -~]")) {
			messages.add("半角文字で入力してください");
		}

		if(!password.equals(checkPassword)) {
			messages.add("確認用のパスワードが違います");
		}

		if(name.isEmpty()) {
			messages.add("名前を入力してください");
		} else if(10 < name.length()) {
			messages.add("１０文字以下で入力してください");
		}

		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}

	}


}
