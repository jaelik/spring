<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script>
			function send_check(){
				var f = document.f; //f라는 name을 가진 form태그를 가져온다.
				
				//유효성 검사
				
				f.submit();
			}
		</script>
	</head>
	<body>
		<form name="f" method="post" action="insert.do">
			<table border="1">
				<caption>:::새 글 쓰기:::</caption>
				
				<tr>
					<th>제목</th>
					<td><input name="subject" style="width:343px;"></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td><input name="name" style="width:343px;"></td>
				</tr>
				<tr>
					<th>내용</th>
					<!-- 가로로 50글자 세로로 엔터 10번정도 칠 수 있는 크기 -->
					<td><textarea name="content" row="10" cols="50" style="resize:none;"></textarea></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input name="pwd" type="password"></td>
				</tr>
				<tr>
					<td colspan="2">
						<img src="resources/img/btn_reg.gif" style="cursor:pointer;" onclick="send_check();">
						<img src="resources/img/btn_back.gif" style="cursor:pointer;" onclick="location.href='board_list.do'">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>






