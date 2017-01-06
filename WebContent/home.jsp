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
</head>
<body>
<h2>ホーム画面</h2>
	<div class="header">
			ユーザー：<c:out value="${user.name}"/><br />
		<c:if test="${ not empty loginUser }">
			<a href="newMessage" >新規投稿</a>
			<a href="" >ユーザー管理</a>
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
		<div class="comments-area">
			<%-- <c:if test="${ not empty loginUser }"> --%>
				<form action="newComment" method="post">
					記事選択<br />
					<textarea name="comment" cols="100" rows="5" class="comment-box"></textarea>
					<br />
					<input type="submit" value="投稿">(500文字まで)
				</form>
			<%-- </c:if> --%>
		</div>
	</div>

</body>
</html>