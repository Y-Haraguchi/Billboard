<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン画面</title>
</head>
<body>
<h3>ログイン画面</h3>
<div class="loginHeader">
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

	<c:if test="${ not empty errorMessages }">
		<div class="errorMessages">
			<ul>
				<c:forEach items="${ errorMessages }" var="messages">
					<li><c:out value="${ messages }" /></li>
				</c:forEach>
			</ul>
		</div>
		<c:remove var="errorMessage" scope="session"/>
	</c:if>

	<form action="login" method="post"><br />
		<label for="login_id">ログインID</label>
		<input name="login_id" type="text" id="login_id" /><br />

		<label for="password">パスワード</label>
		<input name="password" type="password" id="password" /><br />

		<input type="submit" value="ログイン" /><br />
	</form>
</div>
</body>
</html>