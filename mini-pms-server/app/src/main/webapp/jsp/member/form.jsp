<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>새 회원</title>
</head>
<body>
<h1>새 회원</h1>
<form action="add" method="post" enctype="multipart/form-data">
이름: <input type="text" name="name"><br>
이메일: <input id="f-email" type="email" name="email"><button id="checkBtn" type="button">중복검사</button><br>
암호: <input type="password" name="password"><br>
사진: <input type="file" name="photo"><br>
전화번호: <input type="tel" name="tel"><br>
<input type="submit" value="등록">
</form>

<script>
document.querySelector("#checkBtn").onclick = function() {
	var fEmail = document.querySelector("#f-email");
	var xhr = new XMLHttpRequest();
  xhr.open("GET", "check?email=" + fEmail.value, false);
  xhr.send();
  if (xhr.responseText == "yes") {
	  alert("이미 있는 사용자입니다.");
	  fEmail.value = "";
  } else {
	  alert("이 이메일을 사용할 수 있습니다.") 
  }
};
</script>


</body>
</html>