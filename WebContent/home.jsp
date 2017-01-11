<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ホーム画面</title>
<script type="text/javascript">
function isDeleteMessageCheck() {
	if(window.confirm('記事を削除しますか？')) {
		return true;
	} else {
		window.alert('キャンセルされました');
		return false;
	}
}

function isDeleteCommentCheck() {
	if(window.confirm('コメントを削除しますか？')) {
		return true;
	} else {
		window.alert('キャンセルされました');
		return false;
	}
}

</script>

</head>
<body>
<h2>ホーム画面</h2>
	<div class="header">
		<c:if test="${ not empty loginUser }">
			<a href="newMessage" >新規投稿</a>
			<a href="usersManager" >ユーザー管理</a>
			<a href="" >ログアウト</a>
		</c:if><br />

		<div class="category_area">
			カテゴリー ： <input name="category" id="category">
			<input type="submit" value="カテゴリー検索">
		</div><br />

		<%-- <c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="messages">
						<li><c:out value="${messages}" />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session"/>
		</c:if> --%>

	</div>
	<div class="messages">
		<c:forEach items="${messages}" var="message">
			<hr size="10" color="#0000ff" noshade><br />
			メッセージID:<c:out value="${message.message_id}"/><br />
			投稿記事
			<form action="home" method="post" onSubmit="return isDeleteMessageCheck()">
				<input type="hidden" name="message_id" value="${message.message_id}">
				<input type="submit" value="削除">
			</form>
			<br />
			タイトル：<c:out value="${message.title}"/><br />
			投稿者：<c:out value="${message.name}"/><br />
			投稿日時：<fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /><br />
			投稿内容：<br />
			<c:out value="${message.body}"/><br />
			<hr><br /><br />
			コメント
			<c:forEach items="${comments}" var="comment">
				<c:if test="${message.message_id == comment.message_id}">
				コメントID:<c:out value="${comment.id}"/><br />
				投稿者：<c:out value="${comment.name}"/><br />
				コメント内容：<br />
				<c:out value="${comment.body}"/><br />
				<form action="home" method="post" onSubmit="return isDeleteCommentCheck()">
					<input type="hidden" name="comment_id" value="${comment.id}">
					<input type="submit" value="削除">
				</form>

					<hr><br />
				</c:if>
			</c:forEach>

			<div class="comments-area">
				<form action="newComment" method="post">
					<textarea name="commentBody" cols="50" rows="5" class="comment-box"></textarea>
					<br />
					<input type ="hidden" name="messages_id" value="${message.message_id}"/>
					<input type="submit" value="コメント投稿">(500文字まで)
				</form>
			</div>
			<hr size="10" color="#0000ff" noshade><br />
		</c:forEach>

	</div>

</body>
</html>