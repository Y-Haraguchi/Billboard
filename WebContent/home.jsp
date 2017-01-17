<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
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
	<div class="home">
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
		<div class="homeHeader">
			<a href="newMessage" >新規投稿</a>
			<a href="usersManager">ユーザー管理</a>
			<a href="logout" >ログアウト</a>
			<br />
			<br />
		</div>

		<h4>絞り込み</h4>
		<div class="specialize">
			<hr>
			<form action="home" method="get">
				カテゴリー：<input type="text" name="category">
				日付：<input type="date" name="startDate">  ～  <input type="date" name="endDate">
				<input type="submit" value="検索">
			</form>
		</div>

			<h4>投稿記事一覧</h4>
			<c:forEach items="${messages}" var="message">
				<div class="messages">
					<label for="title">タイトル：<c:out value="${message.title}"/></label>
					<label for="subTitle">
						カテゴリー：<c:out value="${message.category}"/>
						　　投稿者：<c:out value="${message.name}"/>
						　　投稿日時：<fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" />
					</label>
				</div>
				<form action="home" method="post" onSubmit="return isDeleteMessageCheck()">
					<input type="hidden" name="message_id" value="${message.message_id}">
					<input type="hidden" name="message_id" value="${comment.id}">
					<div class="postDelete"><input type="submit" value="記事を削除"></div>
				</form>
				<label for="subTitleBody">投稿内容</label><br />
				<label for="body"><c:out value="${message.body}"/><br /></label>
				<hr>
				<label for="subTitleBody">コメント</label>
				<c:forEach items="${comments}" var="comment">
					<c:if test="${message.message_id == comment.message_id}">
						投稿者：<c:out value="${comment.name}"/><br />
						<label for="commentBody"><c:out value="${comment.body}"/></label><br />
						<form action="home" method="post" onSubmit="return isDeleteCommentCheck()">
							<input type="hidden" name="comment_id" value="${comment.id}">
							<div class="commentDelete"><input type="submit" value="削除"></div>
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
						<div class="comment">
							<input type="submit" value="コメント投稿">
						</div>
						<label for="commentCaharaLimit">(500文字まで)</label>
					</form>
				</div>
				<br />
			</c:forEach>
		</div>
	</div>

</body>
</html>