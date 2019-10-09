<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="../common/header.jsp" flush="false"></jsp:include>
	
	
	<div class="row">
	
	
		<div class="col-md-12">
			<h4>List</h4>
			
			<div class="card-deck mb-3 text-center">
				<div class="card md-4 shadow-sm">
					<div class="card-body ">
						<form>
						<ul class="list-inline">
							<li class="col-md-2">
								<select class="form-control">
									<option value="">::선택하세요::</option>
									<option value="">제목</option>
									<option value="">아이디</option>
								</select>
							</li>
							<li class="col-md-6">
								<input class="form-control"  type="text" name="keyword" id="keyword" placeholder="검색어를 입력하세요." />
							</li>
							<li class="col-md-1 vertical-top">
								<a class="form-control">확인</a>
							</li>
						</ul>
						</form>
					</div>
				</div>
			</div>
			
			
			<table class="table table-bordered">
				<tr>
					<td>번호</td>
					<td>제목</td>
					<td>작성자</td>
					<td>작성일</td>
				</tr>
				<tr>
					<td>1</td>
					<td>글올립니다</td>
					<td>홍길동</td>
					<td>0000-00-00 00:00:00</td>
				</tr>
				<tr>
					<td>2</td>
					<td>글올립니다</td>
					<td>홍길동</td>
					<td>0000-00-00 00:00:00</td>
				</tr>
				<tr>
					<td>3</td>
					<td>글올립니다</td>
					<td>홍길동</td>
					<td>0000-00-00 00:00:00</td>
				</tr>
				<tr>
					<td>4</td>
					<td>글올립니다</td>
					<td>홍길동</td>
					<td>0000-00-00 00:00:00</td>
				</tr>
				<tr>
					<td>5</td>
					<td>글올립니다</td>
					<td>홍길동</td>
					<td>0000-00-00 00:00:00</td>
				</tr>
			</table>
			
		</div>
		
		<div class="col-md-12">
			<ul class="list-inline">
				<li>
					<nav aria-label="Page navigation example">
						<ul class="pagination">
							<li class="page-item"><a class="page-link" href="#">Prev</a></li>
							<li class="page-item"><a class="page-link" href="#">1</a></li>
							<li class="page-item"><a class="page-link" href="#">1</a></li>
							<li class="page-item"><a class="page-link" href="#">Next</a></li>
						</ul>
					</nav>
				</li>
				<li class="float-r">
					<a href="/BoardServlet?command=bbsWrite" class="btn btn-primary">글쓰기</a>
				</li>
			</ul>
		</div>
		
	</div>
	
<jsp:include page="../common/footer.jsp" flush="false"></jsp:include>

