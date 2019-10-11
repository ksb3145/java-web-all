<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="../common/header.jsp" flush="false"></jsp:include>
	
	<div class="row">
	
		<div class="col-md-12">
			<input type="hidden" id="userId" name="userId" value="${sessionVO.mUserId}" />
			<h4>상세보기</h4>

			<table class="table table-bordered">
				<colgroup>
					<col width="10%" />
					<col width="" />
					<col width="10%" />
					<col width="15%" />
					<col width="10%" />
					<col width="15%" />
				</colgroup>
				<tbody>
					<tr>
						<th>제목</th>
						<td colspan="5">${boardDetail.title}</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td>${boardDetail.userId}</td>
						<th>작성일</th>
						<td>${boardDetail.regDate}</td>
						<th>첨부파일</th>
						<td> - </td>
					</tr>
					<tr>
						<td colspan="6">${boardDetail.content}</td>
					</tr>
				</tbody>
			</table>
			
			<div class="col-md-12">
				<ul class="list-inline">
					<li>
						<a href="/BoardServlet?command=bbsList" class="btn btn-primary">목록</a>
					</li>
					<li class="float-r">
						<c:if test="${sessionVO.mUserId} eq ${boardDetail.userId}">
							<button type="submit" class="btn btn-warning">수정</button>
						        <button type="submit" class="btn btn-warning">삭제</button>
						</c:if>
					</li>
					
					
				</ul>
			</div>
			
			<form class="form-horizontal" action="/BoardServlet" method="post" >
				<input type="text" id="command" name="command" value="commentInsert" />
				<input type="text" id="id" name="id" value="${boardDetail.id}" />
				<div class="card-deck mb-3">
					<div class="card md-4 shadow-sm">
						<div class="card-header">
							<form class="form-inline">
								<ul class="list-inline">
									<li class="col-md-11">
										<div class="form-group">
											<input type="text" class="form-control" id="exampleInputName2" placeholder="comment...">
										</div>
									</li>
									<li class="col-md-1 float-r">
								        <button type="button" id="execute" class="btn btn-warning">등록</button>
									</li>
								</ul>
							</form>
						</div>
						<div class="card-body ">
							<dl class="dl-horizontal">
							  <dt>aaa님</dt>
							  <dd>안녕하세요~</dd>
							</dl>
						</div>
					</div>
				</div>
				
			</form>
		</div>
		
		
		
	</div>


<script>
    $('#execute').click(function(){
    	alert("re");
    	
    	var boardId = $("#");
    	var commentDepth = $("#");
    	
    	
       /*  $.ajax({
            url:'./time.php',
            success:function(data){
                $('#time').append(data);
            }
        }) */
    })
</script>
	
<jsp:include page="../common/footer.jsp" flush="false"></jsp:include>

