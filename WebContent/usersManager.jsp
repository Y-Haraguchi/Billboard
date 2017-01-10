<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理画面</title>
</head>
<body>
<h2>ユーザー管理画面</h2>

<div class="header">
	<c:if test="${ not empty loginUser }">
		<a href="signup" >ユーザー新規登録</a>
	</c:if><br />
</div>

<div class="usersInformation">
	<form action="userManager" method="post">
		<table border="1" width="500" cellspacing="0" cellpadding="5" bordercolor="#333333">
		<tr>
		<th>ユーザー名</th>
		<th>ログインID</th>
		<th>ユーザー停止・復帰</th>
		</tr>
		<c:forEach items="${enteredUsers}" var="enteredUser">
			<tr>
			<td><a href="editUser?user_id=${enteredUser.id}" >${enteredUser.name}</a></td>
			<td>${enteredUser.loginId}</td>
			<td><input type="button" value="停止"><input type="button" value="復帰"></td>
			</tr>
		</c:forEach>
		</table>
	</form>
</div>


</body>
</html>