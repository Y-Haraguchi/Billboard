package billboard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import billboard.beans.Message;
import billboard.beans.User;
import billboard.service.NewMessageService;

@WebServlet(urlPatterns = { "/newMessage" })
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException  {

		request.getRequestDispatcher("newMessage.jsp").forward(request, response);

	}


	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException  {

		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		//newMessage.jspからカテゴリ、タイトル、本文の入力されたデータを受け取る
		Message message = new Message();
		message.setCategory(request.getParameter("category"));
		message.setTitle(request.getParameter("messageTitle"));
		message.setBody(request.getParameter("messageBody"));
		message.setUser_id(user.getId());

		new NewMessageService().register(message);
		session.setAttribute("recordMessage", message);
		response.sendRedirect("home");

	}

}
