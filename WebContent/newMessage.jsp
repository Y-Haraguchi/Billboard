<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/newMessagePage.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿画面</title>
</head>
<body>
<div id="top">
	<div id="header">
		<h1>新規投稿</h1>
	</div>
	<div id="menu">
		<ul>
			<li>
				<a href="home" >ホーム</a>
			</li>
		</ul>
	</div>
	<br>
	<div id="contents">
		<div id="main">
			<c:if test="${ not empty errorMessages }">
				<div class="errorMessages">
					<ul>
						<c:forEach items="${errorMessages}" var="message">
							<h6><c:out value="${message}" /></h6><br />
						</c:forEach>
					</ul>
				</div>
				<c:remove var="errorMessages" scope="session"/>
			</c:if>
			<br>
			<form action="newMessage" method="post">
				カテゴリー
				<input name="category" type="text" id="category" value="${nowMessageCategory}" >
				(10文字以内で入力してください)
				<br />
				<br />
				タイトル
				<input name="messageTitle" type="text" id="messageTitle" value="${nowMessageTitle}" >
				(50文字以内で入力してください)
				<br />
				<br />
				<h3></h3>
				<br />
				本文
				<textarea name="messageBody" cols="95" rows="25" wrap="hard"><c:out value="${nowMessageBody}" /></textarea>
				<br>
				<br>
				<input type="submit" value="投稿">(1000文字まで)
			</form>
		</div>
	</div>
</div>

</body>
</html>