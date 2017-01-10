package billboard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import billboard.beans.User;
import billboard.service.UserService;

@WebServlet(urlPatterns = { "/usersManager" })
public class UsersManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<User> users = new UserService().getUserList();

		request.setAttribute("enteredUsers", users);

		request.getRequestDispatcher("/usersManager.jsp").forward(request, response);
	}
//
//	@Override
//	protected void doPost(HttpServletRequest request,
//			HttpServletResponse response) throws IOException, ServletException {
//
//	}

}
