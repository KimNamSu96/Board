<%@page import="config.JWTOkens"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	HttpServletRequest req = (HttpServletRequest) request;
	String token=JWTOkens.getToken(req, req.getServletContext().getInitParameter("COOKIE-NAME"));
	boolean isAuthenticated=JWTOkens.verifyToken(token);
%>
<jsp:include page="/WEB-INF/template/Top.jsp" />
<div class="container my-container">
	<div class="jumbotron my-jumbotron">
		<h1 class="inline-h1">환영합니다</h1>
	</div>
</div>