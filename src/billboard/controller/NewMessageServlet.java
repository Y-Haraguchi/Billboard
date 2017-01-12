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
		List<String> messages = new ArrayList<String>();

		if(isValid(request, messages) == true) {
			User user = (User)session.getAttribute("loginUser");
			//newMessage.jspからカテゴリ、タイトル、本文の入力されたデータを受け取る
			Message message = new Message();
			message.setCategory(request.getParameter("category"));
			message.setTitle(request.getParameter("messageTitle"));
			message.setBody(request.getParameter("messageBody"));
			message.setUser_id(user.getId());
			new NewMessageService().register(message);
			response.sendRedirect("home");
		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("newMessage");
		}
	}

	private boolean isValid(HttpServletRequest request,
			List<String> messages) {
		String category = request.getParameter("category");
		String title = request.getParameter("messageTitle");
		String body = request.getParameter("messageBody");

		if(category.isEmpty()) {
			messages.add("カテゴリーを入力してください");
		} else if(10 < category.length()) {
			messages.add("カテゴリーを１０文字以内で入力してください");
		}
		if(title.isEmpty()) {
			messages.add("タイトルを入力してください");
		} else if(50 < title.length()) {
			messages.add("タイトルを５０文字以内で入力してください");
		}
		if(body.isEmpty()) {
			messages.add("本文を入力してください");
		}else if(1000 < body.length()) {
			messages.add("本文を１０００文字以内で入力してください");
		}
		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}

	}

}
