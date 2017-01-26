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

import org.apache.commons.lang.StringUtils;

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
		List<String> messages = new ArrayList<String>();

		if(isGetValid(request, messages) == true) {
			int editUserId = Integer.parseInt(request.getParameter("user_id"));
			User editUser = new UserService().getEditUser(editUserId);
			List<Branch> branches = new BranchService().getBranches();
			List<AssignType> assignTypes = new AssignTypeService().getAssignTypes();
			session.setAttribute("branchList", branches);
			session.setAttribute("assignTypeList", assignTypes);
			session.setAttribute("editUser", editUser);
			request.getRequestDispatcher("/editUser.jsp").forward(request, response);
		} else {
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("/usersManager.jsp").forward(request, response);
		}
	}

	private boolean isGetValid(HttpServletRequest request,
			List<String> messages) {
		int editUserId = Integer.parseInt(request.getParameter("user_id"));
		User editUser = new UserService().getEditUser(editUserId);

		if(editUser == null) {
			messages.add("不正なアクセスがありました");
		}
		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		User loginUser = (User)session.getAttribute("loginUser");

		User editUser = getEditUser(request);

		if(isValid(request, messages) == true) {
			new EditUserService().update(editUser, loginUser);
			session.setAttribute("editUser", editUser);
			response.sendRedirect("usersManager");

		} else {
			session.setAttribute("nowEditUser", editUser);
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("/editUser.jsp").forward(request, response);
		}

	}


	private User getEditUser(HttpServletRequest request)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		User editUser = (User)session.getAttribute("editUser");

		editUser.setId(editUser.getId());
		editUser.setLoginId(request.getParameter("login_id"));
		editUser.setPassword(request.getParameter("password"));
		editUser.setName(request.getParameter("name"));
		editUser.setBranchId(Integer.parseInt(request.getParameter("branch_id")));
		editUser.setAssignTypeId(Integer.parseInt(request.getParameter("assign_type_id")));
		return editUser;

	}

	private boolean isValid(HttpServletRequest request,
			List<String> messages) {
		int userId = Integer.parseInt(request.getParameter("user_id"));
		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkPassword");
		String name = request.getParameter("name");

		//すでにDBに登録されているlogin_idを検索
		User signedUser = new UserService().getUsersLoginId(loginId);
		System.out.println(password);
		System.out.println(checkPassword);
		if(signedUser != null) {
			if(signedUser.getId() != userId) {	//取ってきたデータが自分だったら編集OK
				messages.add("他ユーザーのログインIDと重複しています");
			}
		}
		if(loginId.isEmpty()) {
			messages.add("ログインIDを入力してください");
		}else if(!loginId.matches("^\\w{6,20}$")) {
			messages.add("ログインIDは半角英数字で入力してください");
		}

		if(!StringUtils.isEmpty(password)) {
			if(6 > password.length() || password.length() > 255) {
				messages.add("登録用パスワードは６文字以上２５５文字以下で入力してください");
			} else if(password.matches("[ -~]")) {
				messages.add("半角文字で入力してください");
			}
		}
		if(!StringUtils.isEmpty(password)) {
			if(!password.equals(checkPassword)) {
				messages.add("確認用パスワードの未入力又は、間違っています");
			}
		}

		if(name.isEmpty()) {
			messages.add("ユーザーネームを入力してください");
		} else if(10 < name.length()) {
			messages.add("ユーザーネームは１０文字以下で入力してください");
		}

		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}

	}

}
