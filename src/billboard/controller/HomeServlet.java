package billboard.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

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
		String category = request.getParameter("category");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");


		//カテゴリーがnullだった場合、空の文字列をセット
		if(StringUtils.isEmpty(category)) {
			category = "";
		}

		//絞りむスタート日時をセット
		if(StringUtils.isEmpty(startDate)) {
			//DBからinsert_dateの最小値を取得
			Timestamp minDate = new NewMessageService().selectMinDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			startDate = sdf.format(minDate);
		}

		//endの日時をセット
		if(StringUtils.isEmpty(endDate)) {
			//現在の日時を取得
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			endDate = sdf.format(date);
		}

		//すべてのメッセージを表示
		List<UserMessage> messages = new NewMessageService().getNallowMessages(category, startDate, endDate);
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
