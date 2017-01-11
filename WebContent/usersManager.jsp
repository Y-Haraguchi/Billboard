<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<h2>ユーザー管理画面</h2>

<div class="header">
	<c:if test="${ not empty loginUser }">
		<a href="signup" >ユーザー新規登録</a>
	</c:if><br />
</div>

<div class="usersInformation">
		<table border="1" width="500" cellspacing="0" cellpadding="5" bordercolor="#333333">
		<tr>
		<th>ユーザー名</th>
		<th>ログインID</th>
		<th>ユーザー停止・復帰</th>
		<th>登録日時</th>
		<th>最終更新日</th>
		</tr>
		<c:forEach items="${enteredUsers}" var="enteredUser">
			<tr>
			<td><a href="editUser?user_id=${enteredUser.id}" >${enteredUser.name}</a></td>
			<td>${enteredUser.loginId}</td>
			<td>
			<c:if test="${enteredUser.isBan == 1}">
				<form action="usersManager" method="post" onSubmit="return isStopCheck()">
						<input type="hidden" name="user_id" value="${enteredUser.id}">
						<input type="hidden" name="isBan" value="0">
						<input type="submit" value="停止">
				</form>
			</c:if>
			<c:if test="${enteredUser.isBan == 0}">
				<form action="usersManager" method="post" onSubmit="return isComebackCheck()">
					<input type="hidden" name="user_id" value="${enteredUser.id}">
					<input type="hidden" name="isBan" value="1">
					<input type="submit" value="復帰">
				</form>
			</c:if>
			</td>
			<td>${enteredUser.insertDate}</td>
			<td>${enteredUser.updateDate}</td>
			</tr>
		</c:forEach>
		</table>

</div>


</body>
</html>