<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/template/Top.jsp" />
<div class="container my-container">
	<div class="jumbotron my-jumbotron">
		<h1 class="inline-h1">게시판</h1>
		<h4 class="inline-h4">등록페이지</h4>
	</div>
	<fieldset id="my-write-field">
	<c:if test="${empty record }">
		<form action="<%=request.getContextPath() %>/board/Write.do" method="POST" enctype="multipart/form-data">
	</c:if>
	<c:if test="${not empty record }">
		<form action="<%=request.getContextPath() %>/board/Edit.do" method="POST">
	</c:if>
			<div class="form-group my-write">
				<label for="title">제목</label> <input type="text"
					class="form-control my-write-text" id="title" name="title" value="${record.title }">
			</div>
			<div class="form-group my-write">
				<label for="title">내용</label>
				<textarea class="form-control my-write-txtArea" name="content">${record.content }</textarea>
			</div>
			<c:if test="${empty record }">
				<div class="form-group my-write">
					<label>파일</label> <input type="file"
						class="form-control-file border" name="files" multiple>
				</div>
			</c:if>
			<c:if test="${not empty record }">
				<p class="text-center">파일은 수정불가</p>
			</c:if>
			<div class="form-group my-write">
			<c:if test="${empty record }">
				<button class="btn btn-primary" id="writeBtn" type="submit">글쓰기</button>
			</c:if>
			<c:if test="${not empty record }">
				<button class="btn btn-primary" id="writeBtn" type="submit">수정하기</button>
			</c:if>
			<input type="hidden" name="no" value="${record.no }">
			<input type="hidden" name="username" value="${record.username }">
			</div>
		</form>
	</fieldset>
</div>