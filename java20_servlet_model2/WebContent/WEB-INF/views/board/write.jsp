<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../common/header.jsp" flush="false"></jsp:include>
	
	
	<div class="row">
	
		<div class="col-md-12">
			<h4>글쓰기</h4>
			<form class="form-horizontal" action="/BoardServlet" method="post" enctype="multipart/form-data">
				<c:choose>
					<c:when test="${not empty boardDetail.id && reqFrm ne 'insert'}" >
						<input type="text" id="command" name="command" value="bbsUpdate" />
						<input type="text" id="reqFrm" name="reqFrm" value="${not empty reqFrm ? 'bbsReplyUpdate' : 'bbsUpdate'}" />
						<input type="text" id="boardId" name="boardId" value="${boardDetail.id}" />
						<input type="text" id="userId" name="userId" value="${boardDetail.userId}" />
						<input type="text" id="page" name="page" value="${page}" />
					</c:when>
					<c:otherwise>
						<input type="text" id="command" name="command" value="bbsInsert" />
						<input type="text" id="reqFrm" name="reqFrm" value="${not empty reqFrm ? 'bbsReplyInsert' : ''}" />
						<input type="text" id="userId" name="userId" value="${sessionVO.userId}" />
						<input type="text" id="boardId" name="boardId" value="${boardId}" />
						<input type="text" id="bGroup" name="bGroup" value="${0 eq boardDetail.bGroup ? boardDetail.id : boardDetail.bGroup}" />
					</c:otherwise>
				</c:choose>
				
				
				<div class="form-group">
					<label for="title">제목</label>
					
					<input type="text" class="form-control" id="title" name="title" value="${reqFrm eq 'insert' ? reTitle : boardDetail.title} " placeholder="아이디를 입력하세요." />
				</div>
				
				<div class="form-group">
					<label for="content">내용</label>
					<textarea class="form-control" rows="3"  id="content" name="content">${reqFrm eq 'insert' ?  null : boardDetail.content}</textarea>
				</div>
				
				<div class="form-group">
					<label for="file-upload">파일업로드</label>
					<input type="file" class="form-control" id="file-upload" name="file-upload" />
				</div>
				
				<div class="col-md-12">
					<ul class="list-inline">
						<li>
							<a href="/BoardServlet?command=bbsList" class="btn btn-primary">목록</a>
						</li>
						<li class="float-r">
							<c:choose>
								<c:when test="${not empty boardDetail.id && sessionVO.userId ne null}" >
									<button type="submit" class="btn btn-warning">수정</button>
								</c:when>
								<c:otherwise>
									<button type="submit" class="btn btn-success">등록</button>
								</c:otherwise>
							</c:choose>
						</li>
					</ul>
				</div>
				
			</form>
		</div>
		
		
		
	</div>
	
<jsp:include page="../common/footer.jsp" flush="false"></jsp:include>

