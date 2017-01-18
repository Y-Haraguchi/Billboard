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

import billboard.beans.User;
import billboard.service.LoginService;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.getRequestDispatcher("login.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		//login_idとパスワードをget
		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");
		List<String> messages = new ArrayList<String>();
		User user = new LoginService().login(login_id, password);

		HttpSession session = request.getSession();

		if(isValid(request, messages)) {
			session.setAttribute("loginUser", user);
			response.sendRedirect("home");
		} else {
			String nowLoginId = request.getParameter("login_id");
			request.setAttribute("nowLoginId", nowLoginId);
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
	private boolean isValid(HttpServletRequest request,
			List<String> messages) {
		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");
		User user = new LoginService().login(login_id, password);

		if(user == null) {
			messages.add("ログインに失敗しました");
		}

		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}

	}

}
