<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/editUserPage.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー編集画面</title>
</head>
<body>
<div id="top">
	<div id="header">
		<h1>ユーザー編集画面</h1>
	</div>
	<div id="menu">
		<ul>
			<li><a href="usersManager">←ユーザー管理画面</a></li>
		</ul>
	</div>
	<c:if test="${ not empty errorMessages }">
		<div class="errorMessages">
			<ul>
				<c:forEach items="${errorMessages}" var="messages">
					<h6><c:out value="${messages}" /></h6>
				</c:forEach>
			</ul>
		</div>
		<c:remove var="errorMessages" scope="session"/>
	</c:if>
	<div id="contents">
		<div id="form">
			<form action="editUser" method="post">
				<p>ログインID</p>
				<p class="mail"><input name="login_id" type="text" value="${editUser.getLoginId()}" id="login_id">
				(半角英数字で6文字以上～20文字以下)
				</p>

				<p>ユーザネーム</p>
				<p class="mail"><input name="name" type="text" value="${editUser.name}" id="name">
				(10文字以下)
				<p>
				<p>登録パスワード<p>
				<p class="pass"><input name="password" type="password" id="password" />
				(記号を含む半角文字6文字以上255文字以下)
				<p>確認パスワード<p>
				<p class="pass"><input name="checkPassword" type="password" id="checkPassword" />
				(登録用と同じパスワードを入力してください)
				<p>
				<c:choose>
					<c:when test="${loginUsers.getId() == editUser.getId() && editUser.getAssignTypeId() == 1}">
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
				<input type="hidden"  name="user_id" value="${editUser.getId()}" />
				<p class="submit"><input type="submit" value="変更" /></p>
			</form>
		</div>
	</div>
</div>
</body>
</html>