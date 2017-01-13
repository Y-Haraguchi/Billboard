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
import billboard.exception.NoRowsUpdatedRuntimeException;
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

		if(request.getParameter("user_id") != null) {
			int editUserId = Integer.parseInt(request.getParameter("user_id"));
			User editUser = new UserService().getEditUser(editUserId);
			session.setAttribute("editUser", editUser);
		}

		List<Branch> branches = new BranchService().getBranches();
		List<AssignType> assignTypes = new AssignTypeService().getAssignTypes();

		session.setAttribute("branchList", branches);
		session.setAttribute("assignTypeList", assignTypes);
		request.getRequestDispatcher("/editUser.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		User editUser = getEditUser(request);
		session.setAttribute("editUser", editUser);

		if(isValid(request, messages) == true) {

			try {
				new EditUserService().update(editUser);
			} catch (NoRowsUpdatedRuntimeException e) {
				session.removeAttribute("editUser");
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("editUser");
			}

			session.setAttribute("editUser", editUser);
			session.removeAttribute("editUser");

		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("editUser");
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
		String loginId = request.getParameter("login_id");
		String password = request.getParameter("password");
		String checkPassword = request.getParameter("checkpassword");
		String name = request.getParameter("name");

		if(loginId.isEmpty()) {
			messages.add("ログインIDを入力してください");
		} else if(6 > loginId.length() || loginId.length() > 20) {
			messages.add("６文字以上２０字以下で入力してください");
		}else if(!loginId.matches("\\w")) {
			messages.add("半角英数字で入力してください");
		}

		if(password.isEmpty()) {
			messages.add("パスワードを入力してください");
		} else if(6 > password.length() || password.length() > 255) {
			messages.add("６文字以上２５５文字以下で入力してください");
		} else if(!password.matches("[ -~]")) {
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
