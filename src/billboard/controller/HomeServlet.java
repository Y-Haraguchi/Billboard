package billboard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import billboard.beans.Comment;
import billboard.beans.User;
import billboard.beans.UserComment;
import billboard.beans.UserMessage;
import billboard.service.NewCommentService;
import billboard.service.NewMessageService;

@WebServlet(urlPatterns = { "/home" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		User user = (User)request.getSession().getAttribute("loginUser");
//		UserMessage userMessage = (UserMessage)request.getSession().getAttribute("recordMessage");
//		Message messages = (Message)request.getSession().getAttribute("message");
		List<UserMessage> messages = new NewMessageService().getMessage(user.getId());
		List<UserComment> comments = new NewCommentService().getComment(user.getId());

		request.setAttribute("messages", messages);
		request.setAttribute("comments", comments);
		request.getRequestDispatcher("/home.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		//コメント機能実装
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
//		UserMessage userMessage = (UserMessage)session.getAttribute("recordMessage");
		//home.jspで入力されたコメントをsetしてDBに登録
		Comment comment = new Comment();
		comment.setBody(request.getParameter("commentBody"));
		comment.setUser_id(user.getId());
//		comment.setId(userMessage.getId());

		new NewCommentService().register(comment);
		response.sendRedirect("home");
	}


}
