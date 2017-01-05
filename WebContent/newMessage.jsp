<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://jakarta.apache.org/taglibs/standard/permittedTaglibs"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿画面</title>
</head>
<body>
<h2>新規投稿画面</h2>
<div class="main_contents">
	<div class="category">
		カテゴリー ： <input name="category" id="category">
	</div><br />
	<div class="messageTitle">
		タイトル ： <input name="messageTitle" id="messageTitle">
	</div><br />
	<div class="messageBody">
		本文<br />
		<textarea name="comment" cols="75" rows="25" class="messageBody"></textarea>
		<br />
		<input type="submit" value="投稿">(1000文字まで)
	</div>
</div>

</body>
</html>