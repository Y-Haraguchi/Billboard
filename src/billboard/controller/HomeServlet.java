package billboard.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

		HttpSession session = request.getSession();
		Date startDate = new Date();
		Date endDate = new Date();

		//絞りむスタート日時をセット
		if(request.getParameter("startDate") == null) {
			//nullの場合、スタートをセット
			startDate.setTime(0001/01/01);
		} else {
			//スタート日をセット
			startDate.setTime(request.getDateHeader("startDate"));
		}

		//絞りむ終わりの日時をセット
		if(request.getParameter("endDate") == null) {
			//nullの場合、スタートをセット
			endDate.setTime(9999/99/99);
		} else {
			//スタート日をセット
			endDate.setTime(request.getDateHeader("endDate"));
		}
		//カテゴリー検索のサービスとDAOを実行

		//カテゴリーと日時指定の検索のサービスとDAOを実行

		//すべてのメッセージを表示
		List<UserMessage> messages = new NewMessageService().getMessage();
		List<UserComment> comments = new NewCommentService().getComment();

		session.setAttribute("messages", messages);
		session.setAttribute("comments", comments);

		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {


		if(request.getParameter("message_id") != null) {
			UserMessage userMomment = new UserMessage();
			userMomment.setId(Integer.parseInt(request.getParameter("message_id")));

			new NewMessageService().deleteMessage(userMomment);
		}

		if(request.getParameter("comment_id") != null) {
			UserComment userComment = new UserComment();
			userComment.setId(Integer.parseInt(request.getParameter("comment_id")));

			new NewCommentService().deleteComment(userComment);
		}

		response.sendRedirect("home");
	}

}
