<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー新規登録画面</title>
</head>
<body>
<h2>ユーザー新規登録画面</h2>
<div class="signup">
	<form action="signup" method="post">
		<label for="login_id">ログインID：</label>
		<input name="login_id" id="login_id"><br />
		<label for="name">ユーザネーム：</label>
		<input name="name" id="name"><br />
		<label for="password">登録パスワード</label>
		<input name="password" type="password" id="password" /><br />
		<label for="checkpassword">確認パスワード</label>
		<input name="checkPassword" type="password" id="checkpassword" /><br />


		<input type="submit" value="登録" /><br />
	</form>
</div>


</body>
</html>