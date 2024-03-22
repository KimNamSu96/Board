<%@page import="config.JWTOkens"%>
<%@page import="io.jsonwebtoken.lang.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	boolean isAuthenticated=false;
	HttpServletRequest req = (HttpServletRequest) request;
	String token=JWTOkens.getToken(req, req.getServletContext().getInitParameter("COOKIE-NAME"));
	if(token.trim().length() != 0)
		isAuthenticated=JWTOkens.verifyToken(token);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js"></script>
	<script src="https://kit.fontawesome.com/d89e70aecc.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<title></title>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
		<a class="navbar-brand" href="<%=request.getContextPath()%>/index.jsp"><i class="fa-solid fa-users-between-lines"></i>&nbsp;Project</a>
		<div class="collapse navbar-collapse justify-content-end">
		   <ul class="navbar-nav">
		     <%if(!isAuthenticated) {%>
			     <li class="nav-item">
			       <a class="nav-link" href="<%=request.getContextPath()%>/member/Login.do">로그인</a>
			     </li>
			     <li class="nav-item">
			       <a class="nav-link" href="<%=request.getContextPath()%>/member/Join.do">회원가입</a>
			     </li>
		     <%} %>
		     <%if(isAuthenticated) {%>
		     	<li class="nav-item">
		       		<a class="nav-link" href="javascript:logout()">로그아웃</a>
		     	</li>
		     	<li class="nav-item">
		       		<a class="nav-link" href="<%=request.getContextPath()%>/board/ListPage.do">게시판</a>
		     	</li>
		     	<li class="nav-item">
		       		<a class="nav-link" href="<%=request.getContextPath()%>/member/Edit.do">회원수정</a>
		     	</li>
		     <%} %>
		     <li class="nav-item">
		       <a class="nav-link" href="<%=request.getContextPath()%>/index.jsp">홈</a>
		     </li>
		   </ul>
		</div>
	</nav>
	
<script>
	function logout(){
		var form = document.createElement('form');
		form.method='post';
		form.action='<%=request.getContextPath() %>/member/Logout.do';
		document.body.appendChild(form);
		form.submit();
	}
</script>