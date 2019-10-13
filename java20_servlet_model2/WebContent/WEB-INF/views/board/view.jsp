<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="../common/header.jsp" flush="false"></jsp:include>
	
	<div class="row">
	
		<div class="col-md-12">
			<input type="hidden" id="userId" name="userId" value="${sessionVO.userId}" />
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
						<c:if test="${sessionVO.userId} eq ${boardDetail.userId}">
							<button type="submit" class="btn btn-warning">수정</button>
						        <button type="submit" class="btn btn-warning">삭제</button>
						</c:if>
					</li>
					
					
				</ul>
			</div>
			
			<form class="form-horizontal" action="/BoardServlet" method="post" >
				<input type="text" id="command" name="command" value="commentInsert" />
				<input type="text" id="boardId" name="boardId" value="${boardDetail.id}" />
				<input type="text" id="userId" name="userId" value="${sessionVO.userId}" />
				
				<div class="card-deck mb-3">
					<div class="card md-4 shadow-sm">
						<div class="card-header">
							<form class="form-inline">
								<ul class="list-inline">
									<li class="col-md-11">
										<div class="form-group">
											<input type="text" class="form-control" id="comment" name="comment" placeholder="comment...">
										</div>
									</li>
									<li class="col-md-1 float-r">
								        <button type="button" class="commentSubmit" id="commentSubmit"  class="btn btn-warning" data-cmtdepth="0" data-cmtsort="1" >등록</button>
									</li>
								</ul>
							</form>
						</div>
						<div class="card-body ">
							<ul>
								<li>
									<dl class="dl-horizontal">
										<dt>
											aaa님
											<span class="reComment" data-reDepth="1">댓글</span>									  		
										</dt>
										<dd>안녕하세요~</dd>

									</dl>

								</li>
								<li>
									<dl class="dl-horizontal">
										<dt>
											aaa2님
											<span class="reComment" data-reDepth="1">댓글</span>
										</dt>
										<dd>안녕하세요~2</dd>
										
									</dl>
								</li>
							</ul>
							
						</div>
					</div>
				</div>
				
			</form>
		</div>
				
	</div>

<script>
	$(document).ready(function() {
		// 원글->코멘트
		$(document).on("click",".commentSubmit",function(e){
			var result, comment;
			
	    	var url 		= "/CommentServlet";
	    	var command 	= $("#command").val();
	    	
	    	var boardId 	= $("#boardId").val();
	    	var userId 		= $("#userId").val();
	    	var cmtDepth 	= $(this).data("cmtdepth");
	    	var cmtSort 	= $(this).data("cmtsort");


	    	if(cmtDepth == 0){
	    		comment = $("#comment").val();
	    	}else{
	    		comment = $("#reCommentInput").val();
	    	}
	    	
	    	var data = { command: command, boardId: boardId, userId: userId, comment: comment, cmtDepth: cmtDepth, cmtSort: cmtDepth };

	    	//console.log(data);
	    	
			callAjax(data, url);

		});
		
		//코멘트->코멘트
		$(".reComment").on("click",function(e){
			reCommentClose();
			
			// 상위 dl 태그
			var cmtDepth	= $(this).attr("data-cmtdepth");
			var cmtSort		= $(this).attr("data-cmtsort");
			var parent		= $(this).parents("dl");
			
			//var findTag = parent.parents("ul").find("#addReCommentInput").text();
			
			var html  = "<dd id='addReCommentInput'>";
				html += "	<input type='text' id='reCommentInput' name='reCommentInput' />";
				html += "	<button type='button' class='commentSubmit' class='btn btn-warning' data-cmtdepth='"+cmtDepth+"' data-cmtsort='"+cmtSort+"'>등록</button>";
				html += "	<button type='button' onClick='reCommentClose();'>닫기</button>";
				html += "</dd>";

			parent.append(html);

		});
	});
	
	function reCommentClose(){
		$("#addReCommentInput").remove();
	}

	
    function callAjax(data, url){
    	$.ajax({
			url:url,
		    async:true,
		    type:'POST',
		    data: data,
		    dataType:'json',
		    beforeSend:function(jqXHR) {
		    // 서버 요청 전 호출 되는 함수 return false; 일 경우 요청 중단
		    },
		    success:function(resultData) {
		    // 요청 완료 시
			    console.log(resultData);
		    	if(resultData.resultCode == "OK"){
		    		alert("정상적으로 등록되었습니다.");
		    	}else{
		    		alert("등록실패 다시시도하세요.");		    		
		    	}
		    	
		    	location.reload();
		    },
		    error:function(request,status,error){
		    	alert("등록실패 다시시도하세요.");
			   console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
        });	
    }
</script>
	
<jsp:include page="../common/footer.jsp" flush="false"></jsp:include>

