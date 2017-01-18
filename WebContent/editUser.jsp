<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー編集画面</title>
</head>
<body>
<h2>ユーザー編集画面</h2>
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
<div class="signUpDate">
<div class="editUserHeader">
	<a href="usersManager">ユーザー管理画面へ戻る</a>
</div>
	<form action="editUser" method="post">
		<label for="login_id">ログインID</label>
		<input name="login_id" value="${editUser.getLoginId()}" id="login_id" />
		<br />
		<label for="password">変更パスワード</label>
		<input name="password" type="password" id="password" />
		<br />
		<label for="checkpassword">確認パスワード</label>
		<input name="checkpassword" type="password" id="checkpassword" />
		<br />
		<label for="name">名前</label>
		<input name="name" value="${editUser.name}" id="name" />
		<br />
		<c:choose>
			<c:when test="${editUser.getAssignTypeId() == 1}">
				管理者自身の所属と役職は変更できません。
				<input type="hidden" name="branch_id" value="${editUser.getBranchId()}">
				<input type="hidden" name="assign_type_id" value="${editUser.getAssignTypeId()}">
			</c:when>
			<c:otherwise>
				<select name="branch_id">
					<c:forEach items="${branchList}" var="branch">
						<c:if test="${branch.id == editUser.getBranchId()}">
							<option value="${editUser.getBranchId()}" selected>${branch.name}</option>
						</c:if>
						<c:if test="${branch.id != editUser.getBranchId()}">
							<option value="${branch.id}">${branch.name}</option>
						</c:if>
					</c:forEach>
				</select>

				<select name="assign_type_id">
					<c:forEach items="${assignTypeList}" var="assignType">
						<c:if test="${assignType.id == editUser.getAssignTypeId()}">
							<option value="${editUser.getAssignTypeId()}" selected>${assignType.type_name}</option>
						</c:if>
						<c:if test="${assignType.id != editUser.getAssignTypeId()}">
							<option value="${assignType.id}">${assignType.type_name}</option>
						</c:if>
					</c:forEach>
				</select>
			</c:otherwise>
		</c:choose>
		<br />
		<input type="submit" value="変更する">
	</form>
</div>

</body>
</html>