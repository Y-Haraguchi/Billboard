<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/homePage.css" rel="stylesheet" type="text/css">
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
<div id="top">
	<div id="header">
		<h1>ホーム画面</h1>
	</div>
	<div id="contents">
		<div id="main">
			<h2></h2>
			<div class="sample-box-13">
				<form action="home" method="get">
					カテゴリー
					<p class="mail"><input type="text" name="category" value="${nowCategory}"></p>
					日付
					<p class="mail"><input type="date" name="startDate" value="${nowStartDate}">  ～
					<input type="date" name="endDate" value="${nowEndDate}"></p>
					<input type="submit" value="検索">
				</form>
				<div id="searchReset">
					<form action="home" method="get">
						<input type="hidden" name="category" value="">
						<input type="hidden" name="startDate" value="">
						<input type="hidden" name="endDate" value="">
						<p class="mail"><input type="submit" value="リセット"></p>
					</form>
				</div>
			</div>
			<c:if test="${ not empty accessErrorMessages }">
				<ul>
					<c:forEach items="${accessErrorMessages}" var="accessMessages">
						<h6><c:out value="${accessMessages}" /></h6>
					</c:forEach>
				</ul>
			<c:remove var="accessErrorMessages" scope="session"/>
			</c:if>

			<h2>投稿記事一覧</h2>
			<c:forEach items="${messages}" var="message">
				<div id="messages">
					<h4>タイトル：<c:out value="${message.title}"/></h4>
					<h3>
						カテゴリー：<c:out value="${message.category}"/>
						　　投稿者：<c:out value="${message.name}"/>
						　　投稿日時：<fmt:formatDate value="${message.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" />
					</h3>
				</div>
				<c:choose>
					<c:when test="${loginUser.getAssignTypeId() == 2}">
						<form action="home" method="post" onSubmit="return isDeleteMessageCheck()">
							<input type="hidden" name="message_id" value="${message.message_id}">
							<input class="delMessageSubmit" type="submit" value="記事を削除">
						</form>
					</c:when>
					<c:when test="${loginUser.getId() == message.getUser_id()}">
						<form action="home" method="post" onSubmit="return isDeleteMessageCheck()">
							<input type="hidden" name="message_id" value="${message.message_id}">
							<input class="delMessageSubmit" type="submit" value="記事を削除">
						</form>
					</c:when>
					<c:when test="${loginUser.getAssignTypeId() == 3 && loginUser.getBranchId() == message.getBranch_id() && message.getAssign_type_id() == 4 }">
						<form action="home" method="post" onSubmit="return isDeleteMessageCheck()">
							<input type="hidden" name="message_id" value="${message.message_id}">
							<input id="delMessageSubmit" type="submit" value="記事を削除">
						</form>
					</c:when>
					<c:otherwise></c:otherwise>
				</c:choose>
				【本文】<br />
				<div class="messageBlock">
					<c:forEach items="${fn:split(message.body, '
					')}" var="str1">
						<c:out value="${str1}"/><br>
					</c:forEach>
				</div>
				<br>
				<h3></h3>
				【コメント】<br />
				<c:forEach items="${comments}" var="comment">
					<c:if test="${message.message_id == comment.message_id}">
						<div class="commentBlock" >
							投稿者：<c:out value="${comment.name}"/>
							投稿日時：<fmt:formatDate value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm:ss"/><br />
							<c:forEach items="${fn:split(comment.body, '
							')}" var="str2">
								<c:out value="${str2}"/><br>
							</c:forEach>
							<div class="">
								<c:choose>
									<c:when test="${loginUser.getAssignTypeId() == 2}">
										<form action="home" method="post" onSubmit="return isDeleteCommentCheck()">
											<input type="hidden" name="comment_id" value="${comment.id}">
											<input type="submit" value="削除">
										</form>
									</c:when>
									<c:when test="${loginUser.getId() == comment.getUser_id()}">
										<form action="home" method="post" onSubmit="return isDeleteCommentCheck()">
											<input type="hidden" name="comment_id" value="${comment.id}">
											<input type="submit" value="削除">
										</form>
									</c:when>
									<c:when test="${loginUser.getAssignTypeId() == 3 && loginUser.getBranchId() == comment.getBranch_id() && comment.getAssign_type_id() == 4 }">
										<form action="home" method="post" onSubmit="return isDeleteCommentCheck()">
											<input type="hidden" name="comment_id" value="${comment.id}">
											<input type="submit" value="削除">
										</form>
									</c:when>
									<c:otherwise></c:otherwise>
								</c:choose>
							</div>
						</div>
						<br />
					</c:if>
				</c:forEach>
				<br>
				<c:if test="${message.message_id == commentMessageId}">
					<c:if test="${ not empty errorMessages }">
						<ul>
							<c:forEach items="${errorMessages}" var="messages">
								<h6><c:out value="${messages}" /></h6>
							</c:forEach>
						</ul>
						<c:remove var="errorMessages" scope="session"/>
					</c:if>
				</c:if>
				<div id="comments-area">
					<form action="newComment" method="post">
						<textarea name="commentBody" cols="50" rows="5"><c:out value="${nowComment}" /></textarea>
						<br />
						<input type ="hidden" name="messages_id" value="${message.message_id}"/>
						<div id="comment">
							<input type="submit" value="コメント投稿">
						</div>
						(500文字まで)
					</form>
				</div>
				<br />
			</c:forEach>
		</div>
		<div id="sub">
			<div class="section">
				<h2>メニュー</h2>
				<ul>
					<li>
						<a href="newMessage" >新規投稿</a>
					</li>
					<c:if test="${loginUser.getAssignTypeId() == 1}">
						<li>
							<a href="usersManager">ユーザー管理</a>
						</li>
					</c:if>
					<li>
						<a href="logout" >ログアウト</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>

</body>
</html>