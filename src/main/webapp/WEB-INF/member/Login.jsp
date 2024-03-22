<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/template/Top.jsp" />
<div class="container my-container">
	<div class="jumbotron my-jumbotron">
		<h1 class="inline-h1">로그인</h1>
		<h4 class="inline-h4">로그인 하세요</h4>
	</div>
	<fieldset>
		<c:if test="${empty member }">
		<legend>로그인</legend>
			<form action="<%=request.getContextPath()%>/member/Login.do"
				method="POST">
				<div class="form-group my-form">
					<label for="username">아이디</label> <input type="text"
						class="form-control input-space" id="username" name="username">
				</div>
				<div class="form-group my-form">
					<label for="password">암호</label> <input type="password"
						class="form-control input-space" id="password" name="password">
				</div>
				<div class="form-group my-form">
					<div class="input-space">
						<button class="btn btn-primary" id="joinBtn" type="submit">로그인</button>
					</div>
				</div>
			</form>
		</c:if>
	</fieldset>
</div>
<script>
	var form = document.forms[0];
	form.onsubmit=()=>{
		if(form.username.value.trim().length === 0 || form.username.value===""){
			alert('아이디를 입력하세요');
			form.username.focus();
			return false;
		}
		if(form.password.value.trim().length === 0 || form.password.value===""){
			alert('아이디를 입력하세요');
			form.password.focus();
			return false;
		}
	}
</script>