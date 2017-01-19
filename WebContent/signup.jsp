<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./css/signupPage.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー新規登録画面</title>

<script type="text/javascript">
/* 	var select = document.getElementById('branchId');

	select.onchange = function() {
		//選択されているoption要素を取得
		var selectedBranchId = this.options[ this.selectedIndex ];
	}
 */
</script>


</head>
<body>
<div id="top">
	<div id="header">
		<h1>ユーザー新規登録画面</h1>
	</div>
	<div id="menu">
		<ul>
			<li><a href="usersManager">ユーザー管理画面へ</a></li>
		</ul>
	</div>
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
	<div id="contents">
		<div id="form">
			<form action="signup" method="post">
				<p>ログインID</p>
				<p class="mail"><input name="login_id" type="text" value="${signupUser.getLoginId()}" id="login_id">
				(半角英数字で6文字以上～20文字以下)
				</p>

				<p>ユーザネーム</p>
				<p class="mail"><input name="name" type="text" value="${signupUser.name}" id="name">
				(10文字以下)
				<p>
				<p>登録パスワード<p>
				<p class="pass"><input name="password" type="password" id="password" />
				(記号を含む半角文字6文字以上255文字以下)
				<p>確認パスワード<p>
				<p class="pass"><input name="checkPassword" type="password" id="checkPassword" />
				(登録用と同じパスワードを入力してください)
				<p>

				<br />
				<select name="branch_id" id="branchId">
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
				<br />
				<p class="submit"><input type="submit" value="登録" /></p>
			</form>
		</div>
	</div>
</div>

</body>
</html>