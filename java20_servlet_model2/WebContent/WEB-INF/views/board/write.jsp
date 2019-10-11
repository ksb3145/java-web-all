<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="../common/header.jsp" flush="false"></jsp:include>
	
	
	<div class="row">
	
		<div class="col-md-12">
			<h4>글쓰기</h4>
			<!-- form class="form-horizontal" action="/BoardServlet" method="post" enctype="multipart/form-data"-->
			<form class="form-horizontal" action="/BoardServlet" method="post" >
				<input type="text" id="command" name="command" value="bbsInsert" />
				<input type="text" id="userId" name="userId" value="${sessionVO.mUserId}" />
				
				<div class="form-group">
					<label for="title">제목</label>
					<input type="text" class="form-control" id="title" name="title" value="" placeholder="아이디를 입력하세요." />
				</div>
				
				<div class="form-group">
					<label for="content">내용</label>
					<textarea class="form-control" rows="3"  id="content" name="content"></textarea>
				</div>
				
				<!-- div class="form-group">
					<label for="file-upload">파일업로드</label>
					<input type="file" class="form-control" id="file-upload" name="file-upload" />
				</div-->
				
				<div class="col-md-12">
					<ul class="list-inline">
						<li>
							<a href="/BoardServlet?command=bbsList" class="btn btn-primary">목록</a>
						</li>
						<li class="float-r">
							<c:if test="${sessionVO.mUserId ne null}">
								<button type="submit" class="btn btn-success">등록</button>
								<!-- <button type="submit" class="btn btn-warning">수정</button> -->
							</c:if>
						</li>
						
						
					</ul>
				</div>
			</form>
		</div>
		
		
		
	</div>
	
<jsp:include page="../common/footer.jsp" flush="false"></jsp:include>

