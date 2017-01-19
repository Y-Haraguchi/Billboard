<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/loginPage.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン画面</title>
</head>
<body>
<div id="top">
	<div id="header">
		<h1>ログイン画面</h1>
	</div>
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
		<c:remove var="errorMessages" scope="session"/>
	</c:if>
	<div id="form">
		<form action="login" method="post">
			<p>ログインID</p>
			<p class="mail"><input name="login_id" type="text" id="login_id" value="${nowLoginId}" /></p>
			<p>パスワード</p>
			<p class="pass"><input name="password" type="password" id="password" /></p>

			<p class="submit"><input type="submit" value="ログイン" /></p>
		</form>
	</div>
</div>
</body>
</html>