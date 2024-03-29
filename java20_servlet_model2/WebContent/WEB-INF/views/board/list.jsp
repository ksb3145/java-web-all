<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../common/header.jsp" flush="false"></jsp:include>
	<div class="row">
		<!-- s: 게시판 -->
		<div class="col-md-12">
			<h4>List</h4>
			
			<!-- s: 검색 -->
			<div class="card-deck mb-3 text-center">
				<div class="card md-4 shadow-sm">
					<div class="card-body ">
						<form name="frm" id="frm" action="/BoardServlet" method="get">
						<input type="hidden" id="command" name="command" value="bbsList" />
						
						<ul class="list-inline">
							<li class="col-md-2 col-sm-2">
								<select id="searchType" name="searchType" class="form-control">
									<option value="">::선택하세요::</option>
									<option <c:if test="${resultData.searchType eq 'bTitle'}"> selected</c:if> value="bTitle">제목</option>
									<option <c:if test="${resultData.searchType eq 'mUserId'}"> selected </c:if> value="mUserId">아이디</option>
									<option <c:if test="${resultData.searchType eq 'bContent'}"> selected </c:if> value="bContent">본문</option>
								</select>
							</li>
							<li class="col-md-6 col-sm-6">
								<input class="form-control"  type="text" name="keyword" id="keyword" value="${resultData.keyword}"  data-check="text" placeholder="검색어를 입력하세요." />
							</li>
							<li class="col-md-1 col-sm-3 vertical-top">
								<button type="button" class="btn-sm btn-primary" onClick="sfrmSend();">검색</button>
							</li>
						</ul>
						</form>
					</div>
				</div>
			</div>
			<!-- e: 검색 -->
			
			<!-- s: 게시판리스트 -->
			<p>총: ${resultData.totalCnt} 건</p>
			<table class="table table-bordered">
				<tr>
					<td>번호</td>
					<td>제목</td>
					<td>작성자</td>
					<td>작성일</td>
				</tr>
				<c:forEach var="obj" items="${boardList}" varStatus="status" >
					<tr>
						<td>${resultData.rownum-status.index}</td>
						<td>
							<a href="/BoardServlet?command=bbsView&no=${obj.id}&page=${resultData.page}${resultData.location}">${obj.title}</a>
							<c:if test="${obj.commentCNT>0}"> <span style="font-size:10px;">[댓글: ${obj.commentCNT}]</span>  </c:if>
							<c:if test="${obj.fileYN eq 'Y'}"> <span style="font-size:10px;">[파일]</span> </c:if>
						</td>
						<td>${obj.userId}</td>
						<td>${obj.regDate}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty boardList}">
					<tr>
						<td colspan="4" class="text-center">등록된 글이 없습니다.</td>
					</tr>
				</c:if>
			</table>
			<!-- e: 게시판리스트 -->
		<!-- e: 게시판 -->
		
		<!-- s: 페이지번호 -->
		<div class="col-md-12">
			<ul class="list-inline">
				<li>
					<nav aria-label="Page navigation example">
						${resultData.paging}
					</nav>
				</li>
				<li class="float-r">
					<a href="/BoardServlet?command=bbsWriteForm&page=${resultData.page}${resultData.location}" class="btn btn-primary">글쓰기</a>
				</li>
			</ul>
		</div>
		<!-- e: 페이지번호 -->
		
	</div>
	
<jsp:include page="../common/footer.jsp" flush="false"></jsp:include>

