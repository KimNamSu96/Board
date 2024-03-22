<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/template/Top.jsp" />
<div class="container my-container">
	<div class="jumbotron my-jumbotron">
		<h1 class="inline-h1">게시판</h1>
		<h4 class="inline-h4">목록 페이지</h4>
	</div>
	<table class="table">
		<thead class="my-view-table">
			<tr>
				<th class="w-22 bg-dark text-white text-center">번호</th>
				<th class="w-22 bg-dark text-white text-center">제목</th>
				<th class="w-22 bg-dark text-white text-center">작성자</th>
				<th class="w-22 bg-dark text-white text-center">조회수</th>
				<th class="w-22 bg-dark text-white text-center">등록일</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty list }">
				<tr>
					<td>등록한 글이 없습니다.</td>
				</tr>
			</c:if>
			<c:if test="${not empty list }">
				<c:forEach items="${list.records }" var="record">
					<tr>
						<td>${record.no }</td>
						<td><a href="<%=request.getContextPath()%>/board/View.do?no=${record.no}">${record.title }</a></td>
						<td>${record.username }</td>
						<td>${record.visitcount }</td>
						<td>${record.postdate }</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<div class="text-right">
		<a class="btn btn-primary" href="<%=request.getContextPath() %>/board/Write.do">글쓰기</a>
	</div>
	
	<div>
		${list.pagingString }
	</div>
	
	<form action="<%=request.getContextPath() %>/board/ListPage.do" class="form-inline justify-content-center" method="post">
		<select class="form-control" name="searchColumn">
			<option value="title">제목</option>
			<option value="content">내용</option>
			<option value="username">작성자</option>
		</select> 
		<input type="text" class="form-control mx-2 my-2"
			placeholder="검색어를 입력하세요" name="searchWord" />
		<button type="submit" class="btn btn-primary">검색</button>
	</form>
</div>