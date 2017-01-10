<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー編集画面</title>
</head>
<body>
<h2>ユーザー編集画面</h2>
<div class="editUser">
	<form action="editUser" method=post>
		<label for="login_id">ログインID</label>
		<input name="login_id" value="${userLoginId}" id="login_id" />
		<br />
		<label for="password">変更パスワード</label>
		<input name="password" value="${editUser.password}" type="password" id="password" />
		<br />
		<label for="checkpassword">確認パスワード</label>
		<input name="checkpassword" value="${editUser.password}" type="password" id="checkpassword" />
		<br />
		<label for="name">名前</label>
		<input name="name" value="${editUser.name}" id="name" />
		<br />
		<select name="branch_id">
			<c:forEach items="${branchList}" var="branch">
				<option value="${branch.id}">${branch.name}</option>
			</c:forEach>
		</select>

		<select name="assign_type_id">
			<c:forEach items="${assignTypeList}" var="assignType">
				<option value="${assignType.id}">${assignType.type_name}</option>
			</c:forEach>
		</select>
		<br />

	</form>
</div>

</body>
</html>