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
		List<String> messages = new ArrayList<String>();
		Comment comment = new Comment();
		if(isValid(request, messages)) {
			User user = (User)session.getAttribute("loginUser");
			//home.jspで入力されたコメントをsetしてDBに登録
			comment.setBody(request.getParameter("commentBody"));
			comment.setUser_id(user.getId());
			comment.setMessage_id(Integer.parseInt(request.getParameter("messages_id")));

			new NewCommentService().register(comment);
			response.sendRedirect("home");
		} else {
			String nowComment = request.getParameter("commentBody");
			int commentMessageId = Integer.parseInt(request.getParameter("messages_id"));
			request.setAttribute("nowComment", nowComment);
			request.setAttribute("commentMessageId", commentMessageId);
			session.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request,
			List<String> messages) {
		String body = request.getParameter("commentBody");

		if(body.isEmpty()) {
			messages.add("本文を入力してください");
		}else if(500 < body.length()) {
			messages.add("本文を５００文字以内で入力してください");
		}
		if(messages.size() == 0) {
			return true;
		} else {
			return false;
		}

	}


}
