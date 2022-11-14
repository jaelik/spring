<%@page import="vo.PersonVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<% 
		//기존 방식 재확인 
		//1. 기본생성자 + setter 
		PersonVO p1 = new PersonVO();
		p1.setName("홍길동");
		p1.setAge(30);
		p1.setTel("010-1111-111");
		
		//2.오버로딩된 생성자 사용 하기 
		PersonVO p2 = new PersonVO("김길동",20,"010-2222-2222");
		
		//위 객체를 만든것들을  body 에서 el로 표기하기 위해 request영역에 저장 하기
		pageContext.setAttribute("p1", p1);
		request.setAttribute("p2", p2);
	
	%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<p>${p1.name}/${p1.age}/${p1.tel}</p>
		<p>${p2.name}/${p2.age}/${p2.tel}</p>
	</body>
</html>