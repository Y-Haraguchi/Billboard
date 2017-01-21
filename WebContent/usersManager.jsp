<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/userManagerPage.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理画面</title>
<script type="text/javascript">
function isStopCheck() {
	if(window.confirm('ユーザーを停止しますか？')) {
		return true;
	} else {
		window.alert('キャンセルされました');
		return false;
	}
}

function isComebackCheck() {
	if(window.confirm('ユーザーを復帰させますか？')) {
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
		<h1>ユーザー管理</h1>
	</div>
	<div id="menu">
		<ul>
			<li>
				<a href="home" >←ホーム</a>
			</li>
			<li>
				<a href="signup" >ユーザー新規登録→</a>
			</li>
		</ul>
	</div>
	<div id="errorMessage">
		<c:if test="${ not empty errorMessages }">
			<ul>
				<c:forEach items="${ errorMessages }" var="messages">
					<h5><c:out value="${ messages }" /></h5>
				</c:forEach>
			</ul>
			<c:remove var="errorMessages" scope="session"/>
		</c:if>
	</div>
	<div id="main">
		※各ユーザーの名前をクリックするとユーザー編集画面へ遷移します。<br />
		※管理者権限を持つユーザー自身のアカウントは停止できません。
	</div>
	<div id="type08">
		<table class="type08">
			<thead>
			<tr>
				<th>ユーザー名</th>
				<th>ログインID</th>
				<th>所属部署</th>
				<th>役職</th>
				<th>ユーザー停止・復帰</th>
				<th>登録日時</th>
				<th>最終更新日</th>
			</tr>
			</thead>
			<c:forEach items="${enteredUsers}" var="enteredUser">
				<tbody>
				<tr>
				<td><a href="editUser?user_id=${enteredUser.id}" >${enteredUser.name}</a></td>
				<td>${enteredUser.loginId}</td>
				<td>
					<c:forEach items="${branchList}" var="branch">
						<c:if test="${enteredUser.getBranchId() == branch.id}">${branch.name}</c:if>
					</c:forEach>
				</td>
				<td>
					<c:forEach items="${assignTypeList}" var="assignType">
						<c:if test="${enteredUser.getAssignTypeId() == assignType.id}">${assignType.type_name}</c:if>
					</c:forEach>
				</td>
				<td>
				<c:choose>
					<c:when test="${loginUsers.getId() == enteredUser.getId() && enteredUser.getAssignTypeId() == 1}"></c:when>
					<c:otherwise>
						<c:if test="${enteredUser.isBan == 1}"><font color="#32CD32">有効</font></c:if>
						<c:if test="${enteredUser.isBan == 0}"><font color="#ff0000">無効</font></c:if>
						<c:if test="${enteredUser.isBan == 1}">
							<form action="usersManager" method="post" onSubmit="return isStopCheck()">
									<input type="hidden" name="user_id" value="${enteredUser.id}">
									<input type="hidden" name="isBan" value="0">
									<div class="isBan"><input type="submit" value="停止"></div>
							</form>
						</c:if>
					</c:otherwise>
				</c:choose>
				<c:if test="${enteredUser.isBan == 0}">
					<form action="usersManager" method="post" onSubmit="return isComebackCheck()">
						<input type="hidden" name="user_id" value="${enteredUser.id}">
						<input type="hidden" name="isBan" value="1">
						<div class="isBan"><input type="submit" value="復帰"></div>
					</form>
				</c:if>
				</td>

				<td><fmt:formatDate value="${enteredUser.insertDate}" pattern="yyyy年MM月dd日（E）HH時mm分"/></td>
				<td><fmt:formatDate value="${enteredUser.updateDate}" pattern="yyyy年MM月dd日（E）HH時mm分"/></td>
				</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
</div>

</body>
</html>