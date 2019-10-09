<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="../common/header.jsp" flush="false"></jsp:include>
	
	
	<div class="row">
	
		<div class="col-md-12">
			<h4>글쓰기</h4>
			<form action="/boardServlet" method="post" class="form-horizontal">
				<input type="hidden" name="command" value="bbsInsert">
				<div class="form-group">
					<label for="title">제목</label>
					<input type="text" class="form-control" id="title" name="title" value="" placeholder="아이디를 입력하세요." />
				</div>
				
				<div class="form-group">
					<label for="content">내용</label>
					<textarea class="form-control" rows="3"  id="content" name="content"></textarea>
				</div>
				
				<div class="form-group">
					<label for="file-upload">파일업로드</label>
					<input type="file" class="form-control" id="file-upload" name="file-upload" />
				</div>
			</form>
		</div>
		
		<div class="col-md-12">
			<ul class="list-inline">
				<li>
					<button type="submit" class="btn btn-primary">목록</button>
				</li>
				<li class="float-r">
					<button type="submit" class="btn btn-success">등록</button>
					<button type="submit" class="btn btn-warning">수정</button>
				</li>
			</ul>
		</div>
		
	</div>
	
<jsp:include page="../common/footer.jsp" flush="false"></jsp:include>

