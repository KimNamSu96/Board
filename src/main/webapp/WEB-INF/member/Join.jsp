<%@page import="dto.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/template/Top.jsp" />
<%    
	String[] inters = new String[0];
	MemberDto member = (MemberDto)request.getAttribute("member");
	String gender = "";
	if(member != null){
		gender = member.getGender();
		inters = member.getInters();
	}
	
    if (inters == null) {
        inters = new String[0];
    }
%>
<%!
    // 선택된 항목인지 확인하는 메서드
    boolean isSelected(String item, String[] inters) {
        for (String inter : inters) {
            if (item.equals(inter)) {
                return true;
            }
        }
        return false;
    }
%>
<div class="container my-container">
	<c:if test="${empty member }">
		<div class="jumbotron my-jumbotron">
			<h1 class="inline-h1">회원가입</h1>
			<h4 class="inline-h4">회원 가입 하세요</h4>
		</div>
	</c:if>
	<c:if test="${not empty member }">
		<div class="jumbotron my-jumbotron">
			<h1 class="inline-h1">회원정보</h1>
			<h4 class="inline-h4">수정하세요</h4>
		</div>
	</c:if>
	<fieldset>
		<legend>회원가입</legend>
		<c:if test="${empty member}">
		<form action="<%=request.getContextPath() %>/member/Join.do" method="POST">
		</c:if>
		<c:if test="${not empty member}">
		<form action="<%=request.getContextPath() %>/member/Edit.do" method="POST">
		</c:if>
			<div class="form-group my-form">
				<c:if test="${empty member }">
				<label for="username">아이디</label> <input type="text"
					class="form-control input-space" id="username" name="username">
				</c:if>
				<c:if test="${not empty member }">
				<label for="username">아이디</label> <input type="text"
					class="form-control input-space" id="username" name="username"
					value="${member.username }" readonly>
				</c:if>
			</div>
			<div class="form-group my-form">
				<label for="password">암호</label> <input type="password"
					class="form-control input-space" id="password" name="password"
					value="${member.password }">
			</div>
			<div class="form-group my-form">
				성별
				<div class="input-space">
					<div class="custom-control custom-radio custom-control-inline">
						<input type="radio" class="custom-control-input" id="man"
							name="gender" value="M" <%=gender.equals("M") ? "checked" : "" %>> 
						<label class="custom-control-label" for="man">남자</label>
					</div>
					<div class="custom-control custom-radio custom-control-inline">
						<input type="radio" class="custom-control-input" id="woman"
							name="gender" value="W" <%=gender.equals("W") ? "checked" : "" %>> 
						<label class="custom-control-label" for="woman">여자</label>
					</div>
				</div>
			</div>
			<div class="form-group my-form">
				<div class="form-check">
					취미
					<div class="input-chkbox">
						<input class="form-check-input" type="checkbox" name="inters" value="정치"
							id="politics" <%=(isSelected("정치", inters) ? "checked" : "")%>> <label class="form-check-label"
							for="politics">정치</label>
					</div>
				</div>
				<div class="form-check">
					<div class="input-chkbox">
						<input class="form-check-input" type="checkbox" name="inters" value="경제"
							id="economy" <%=(isSelected("경제", inters) ? "checked" : "")%>> <label class="form-check-label"
							for="economy">경제</label>
					</div>
				</div>
				<div class="form-check">
					<div class="input-chkbox">
						<input class="form-check-input" type="checkbox" name="inters" value="연예"
							id="entertainment" <%=(isSelected("연예", inters) ? "checked" : "")%>> <label class="form-check-label"
							for="entertainment">연예</label>
					</div>
				</div>
			</div>
			<div class="form-group my-form">
				학력
				<div class="input-space">
					<c:if test="${empty member }">
						<select class="form-select" name="education">
							<option value="">학력을 선택해주세요</option>
							<option value="중학교">중학교</option>
							<option value="고등학교">고등학교</option>
							<option value="대학교">대학교</option>
						</select>
					</c:if>
					<c:if test="${not empty member}">
						<select class="form-select" name="education">
							<option value="">학력을 선택해주세요</option>
							<option value="중학교" <%=member.getEducation().equals("중학교") ? "selected" : "" %>>중학교</option>
							<option value="고등학교" <%=member.getEducation().equals("고등학교") ? "selected" : "" %>>고등학교</option>
							<option value="대학교" <%=member.getEducation().equals("대학교") ? "selected" : "" %>>대학교</option>
						</select>
					</c:if>
				</div>
			</div>
			<div class="form-group my-form my-custom-txtArea">
				<label id="selfintro">자기소개</label>
				<div class="input-space">
					<textarea class="form-control my-txtArea" name="selfintroduce">${member.selfintroduce }</textarea>
				</div>
			</div>
			<div class="form-group my-form">
				<div class="input-space">
					<button class="btn btn-primary" id="joinBtn" type="submit"><%=member == null ? "회원가입" : "회원수정" %></button>
				</div>
			</div>
		</form>
	</fieldset>
</div>
<script>
	var form = document.forms[0];
	form.onsubmit=()=>{
		if(form.username.value.trim().length === 0 || form.username.value === ""){
			alert('아이디를 입력하세요');
			form.username.focus();
			return false;
		}
		if(form.password.value.trim().length === 0 || form.password.value === ""){
			alert("암호를 입력하세요");
			form.password.focus();
			return false;
		}
		if(form.gender.value.trim().length === 0 || form.gender.value === ""){
			alert("성별을 선택하세요");
			return false;
		}
		var intersTxt = "";
		form.inters.forEach(e=>{
			if(e.checked === true) intersTxt+=e.value;
		});
		if(intersTxt.trim().length === 0 || intersTxt===""){
			alert("취미를 선택하세요");
			return false
		}
		if(form.education.value.trim().length === 0 || form.education.value === ""){
			alert("학력을 선택하세요");
			return false;
		}
		if(form.selfintroduce.value.trim().length === 0 || form.selfintroduce.value === ""){
			alert("자기소개를 입력하세요");
			form.selfintroduce.focus();
			return false;
		}
	}
	
</script>