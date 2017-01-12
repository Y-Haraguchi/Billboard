package billboard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import billboard.beans.Comment;
import billboard.beans.User;
import billboard.service.NewCommentService;

@WebServlet(urlPatterns = {"/newComment"} )
public class NewCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		//コメント機能実装
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		//home.jspで入力されたコメントをsetしてDBに登録
		Comment comment = new Comment();
		comment.setBody(request.getParameter("commentBody"));
		comment.setUser_id(user.getId());
		comment.setMessage_id(Integer.parseInt(request.getParameter("messages_id")));

		new NewCommentService().register(comment);
		session.setAttribute("comments", comment);
		response.sendRedirect("home");

	}

}
