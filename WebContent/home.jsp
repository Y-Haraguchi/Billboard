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
	if(window.confirm('記事を削除するとコメントも削除されますがよろしいでしょうか？')) {
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
<c:if test="${ not empty accessErrorMessages }">
<div class="accessErrorMessages">
	<ul>
		<c:forEach items="${accessErrorMessages}" var="accessMessages">
			<li><c:out value="${accessMessages}" />
		</c:forEach>
	</ul>
</div>
<c:remove var="accessErrorMessages" scope="session"/>
</c:if>

	<div class="header">
		<a href="newMessage" >新規投稿</a>
		<a href="usersManager">ユーザー管理</a>
		<a href="logout" >ログアウト</a>
		<br />
		<br />

		<div class="refinment_area">
			カテゴリー ：
			<select name="messages_category">
				<c:forEach items="${messages}" var="message">
					<option value="${message.category}">${message.category}</option>
				</c:forEach>
			</select>
		</div><br />
		<label>日付<br />
		<input type="date" name="startDate">
		</label>
		～
		<input type="date" name="endDate">
		<input type="submit" value="検索">
	</div>
	<div class="messages">
		<c:forEach items="${messages}" var="message">
			<hr size="10" color="#0000ff" noshade><br />
			メッセージID:<c:out value="${message.message_id}"/><br />
			投稿記事
			<form action="home" method="post" onSubmit="return isDeleteMessageCheck()">
				<input type="hidden" name="message_id" value="${message.message_id}">
				<input type="hidden" name="message_id" value="${comment.id}">
				<input type="submit" value="投稿記事を削除">
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
			<c:if test="${ not empty errorMessages }">
			<div class="errorMessages">
				<ul>
					<c:forEach items="${errorMessages}" var="messages">
						<li><c:out value="${messages}" />
					</c:forEach>
				</ul>
			</div>
			<c:remove var="errorMessages" scope="session"/>
			</c:if>
			<div class="comments-area">
				<form action="newComment" method="post">
					<textarea name="commentBody" cols="50" rows="5" class="comment-box"></textarea>
					<br />
					<input type ="hidden" name="messages_id" value="${message.message_id}"/>
					<input type="submit" value="コメント投稿">(500文字まで)
				</form>
			</div>
			<br />
			<hr size="10" color="#0000ff" noshade><br />
		</c:forEach>
	</div>

</body>
</html>