<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿画面</title>
</head>
<body>
<h2>新規投稿画面</h2>
<div class="newMessageDate">
<div class="homeHeader">
	<a href="home" >ホーム画面へ戻る</a>
</div>
	<c:if test="${ not empty errorMessages }">
		<div class="errorMessages">
			<ul>
				<c:forEach items="${errorMessages}" var="message">
					<li><c:out value="${message}" /><br />
				</c:forEach>
			</ul>
		</div>
		<c:remove var="errorMessages" scope="session"/>
	</c:if>
	<form action="newMessage" method="post">
		<label for="newMessage">カテゴリー</label>
		<input name="category" value="${nowNewMessage.category}" id="category">
		<label for="newMessage">タイトル</label>
		<input name="messageTitle" value="${nowNewMessage.title}" id="messageTitle"><br />
		<label for="newMessage">本文</label>
		<textarea name="messageBody" cols="75" rows="100" class="messageBody">${nowNewMessage.body}</textarea>
		<input type="submit" value="投稿">(1000文字まで)
	</form>
</div>

</body>
</html>