<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/template/Top.jsp"/>
<div class="container my-container">
	<div class="jumbotron my-jumbotron">
		<h1 class="inline-h1">게시판</h1>
		<h4 class="inline-h4">상세페이지</h4>
	</div>
	<table class="table table-bordered">
		<tbody class="table-sm">
			<tr>
				<th class="w-25 bg-dark text-white text-center">번호</th>
				<td>${record.no}</td>
			</tr>
			<tr>
				<th class="w-25 bg-dark text-white text-center">조회수</th>
				<td>${record.visitcount}</td>
			</tr>
			<tr>
				<th class="w-25 bg-dark text-white text-center">글쓴이</th>
				<td>${record.username}</td>
			</tr>
			<tr>
				<th class="w-25 bg-dark text-white text-center">작성일</th>
				<td>${record.postdate}</td>
			</tr>
			<tr>
				<th class="w-25 bg-dark text-white text-center">제목</th>
				<td>${record.title}</td>
			</tr>
			<tr>
				<th class="bg-dark text-white text-center" colspan="2">내 용</th>
			</tr>
			<tr>
				<td colspan="2">${record.content}</td>
			</tr>
			<tr>
				<th class="w-25 bg-dark text-white text-center">파일</th>
				<td class="text-left">
					<c:if test="${not empty files }">
						<c:forEach items="${files }" var="file">
							<a href="<%=request.getContextPath()%>/Down.do?filepath=${file.filepath}">${file.filepath }&nbsp;&nbsp;</a>
						</c:forEach>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>
	<!-- 수정/삭제/목록 컨트롤 버튼 -->
	<form id="delForm" action="<%=request.getContextPath() %>/board/Delete.do" method="post">
		<div class="text-center">
			<c:if test="${record.username eq username }">
				<a href="<%=request.getContextPath() %>/board/Edit.do?no=${record.no}" class="btn btn-success">수정</a> 
				<a href="#" id="delBtn" class="btn btn-success">삭제</a>
			</c:if> 
			<a href="<%=request.getContextPath() %>/board/ListPage.do" class="btn btn-success">목록</a>
		</div>
		<input type="hidden" name="no" value="${record.no }">
		<input type="hidden" name="username" value="${record.username }">
	</form>
</div>
<script>
	document.querySelector('#delBtn').onclick=(e)=>{
		confirm('정말 삭제하시겠습니까?');
		delForm.submit();
	};
</script>