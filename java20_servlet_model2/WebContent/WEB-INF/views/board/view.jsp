<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../common/header.jsp" flush="false"></jsp:include>


<style>

	/*코멘트*/
	.commentArea li:first-child { border-top: none;  }
	.commentArea li { border-top: 1px solid rgba(0, 0, 0, 0.125);  }
	.reComment.btn { margin-left: 10px; }
	
	.commentArea span.date { font-size: 11px; color: gray; padding-left: 5px; }
	.commentArea p { margin-bottom: 5px; }
	
	.commentArea dl { margin-bottom: 0px; }
	.commentArea dt, .commentArea dd { display: inline-block; }
	.commentArea dt { vertical-align: top; }

	.commentArea dd#addReCommentInput { display: block;  }
				
</style>
	
	<div class="row">
	
		<div class="col-md-12">
			<h4>상세보기</h4>
			<!-- s: 게시판 form -->
			
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
					<form name="bFrm" action="/BoardServlet?command=bbsWriteForm" method="post" >
						<input type="hidden" name="page" value="${page}" />
						<input type="hidden" id="boardId" name="boardId" value="${boardDetail.id}" />
						<input type=hidden id="userId" name="userId" value="${sessionVO.userId}" />
						
						<ul class="list-inline">
							<li>
								<a href="/BoardServlet?command=bbsList&page=${page}" class="btn lg gray">목록</a>
							</li>
							<li class="float-r">
								<c:if test="${sessionVO.userId eq boardDetail.userId}">
									<c:if test="${not empty boardDetail.pid}">
										<input type="hidden" id="reqFrm" name="reqFrm" value="bbsUpdateForm" />
									</c:if>
									<button type="submit" class="btn lg green">수정</button>
								    <button type="button" class="btn lg red delBtn" data-command="bbsDelete">삭제</button>
								</c:if>

								<c:if test="${sessionVO.userId ne boardDetail.userId && boardDetail.pid eq 0}">
									<input type="hidden" id="reqFrm" name="reqFrm" value="insert" />
									<button type="submit" class="btn lg green" >답글</button>
								</c:if>
								
							</li>
						</ul>
					</form>
				</div>
			<!-- e: 게시판 form -->
			
			
			<!-- s: 댓글 form -->
			<form class="form-horizontal" action="/BoardServlet" method="post" >
				<input type="hidden" id="command" name="command" value="commentInsert" />
				<input type="hidden" id="boardId" name="boardId" value="${boardDetail.id}" />
				<input type="hidden" id="userId" name="userId" value="${sessionVO.userId}" />

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
								        <button type="button" class="commentSubmit btn lg green" id="commentSubmit"  class="btn btn-warning" data-cmtgroup="0" data-cmtdepth="0" data-cmtsort="1">등록</button>
									</li>
								</ul>
							</form>
						</div>
								
						<div class="commentArea card-body ">
							<ul>
								<c:forEach var="obj" items="${commentList}">
									<li>
										<dl class="dl-horizontal" style="padding-left: ${obj.depth}rem;">
											<dt>
												<c:if test="${obj.depth>0}">└</c:if>
											</dt>
											<dd>
												${obj.userId} <span class="date">${obj.regdate}</span>
												<c:if test="${sessionVO.userId == obj.userId}">	
													${obj.cId}/${obj.pId}/${obj.depth}
													<br/>
													<span class="delBtn btn sm red" data-cid="${obj.cId}" data-depth="${obj.depth}" data-command="cmtDelete">삭제</span>
												</c:if>
												<span class="reComment btn sm black mgReset" data-cid="${obj.cId}" data-cmtdepth="${obj.depth}" data-cmtsort="${obj.sort}" data-cmtgroup="${obj.cmtGroup}">댓글</span>
												<p>${obj.contents}</p>  	
											</dd>
										</dl>
									</li>
								</c:forEach>
							</ul>
						</div>
						
					</div>
				</div>
				
			</form>
			<!-- e: 댓글 form -->
		</div>
				
	</div>


<script>
	$(document).ready(function() {
		// 원글->코멘트
		$(document).on("click",".commentSubmit",function(e){
			var comment, pid;
			
	    	var url 		= "/CommentServlet";
	    	var command 	= $("#command").val();
	    	var boardId 	= $("#boardId").val();
	    	var userId 		= $("#userId").val();
	    	var cmtGroup 	= $(this).data("cmtgroup");
	    	var cmtDepth 	= $(this).data("cmtdepth");
	    	var cmtSort 	= $(this).data("cmtsort");
	    	


	    	if(cmtDepth == 0){
	    		pid 	= 0;
	    		comment = $("#comment").val();
	    	}else{
	    		pid 	= $(this).data("cid");
	    		comment = $("#reComment").val();
	    	}
	    	
	    	var data = { pid: pid, command: command, boardId: boardId, userId: userId, comment: comment, cmtGroup: cmtGroup, cmtDepth: cmtDepth, cmtSort: cmtSort };

	    	console.log(data);
	    
			callAjax(data, url);

		});
		
		//코멘트->코멘트
		$(".reComment").on("click",function(e){
			reCommentClose();
			
			// 상위 dl 태그
			var cid			= $(this).attr("data-cid");
			var cmtGroup	= $(this).attr("data-cmtgroup");
			var cmtDepth	= $(this).attr("data-cmtdepth");
			var cmtSort		= $(this).attr("data-cmtsort");
			var parent		= $(this).parents("dl");
			cmtDepth++;
			//var findTag = parent.parents("ul").find("#addReCommentInput").text();
			
			var html  = "<dd id='addReCommentInput'>";
				 html += "	<input type='text' id='reComment' name='reComment' />";
			 	 html += "	<button type='button' class='commentSubmit' class='btn btn-warning' data-cid='"+cid+"' data-cmtgroup='"+cmtGroup+"' data-cmtdepth='"+ cmtDepth +"' data-cmtsort='"+ cmtSort +"'>등록</button>";
				 html += "	<button type='button' onClick='reCommentClose();'>닫기</button>";
				 html += "</dd>";

			parent.append(html);
		});
		
		//게시글 삭제
		$(".delBtn").on("click",function(e){
			var url = "", data = "";
			var command = $(this).attr("data-command");
	    	var boardId = $("#boardId").val();
	    	var userId 	= $("#userId").val();
	    	
			if(command == "bbsDelete"){
				// 게시글 삭제 : boardId 값으로 게시글 + (게시글키값으로) 코멘트 삭제
				url = "/BoardServlet";
				data = { command:command, boardId:boardId, userId:userId };
			}else if(command == "cmtDelete"){
				// 코멘트 삭제
				var cmtId = $(this).data("cid");
				var cmtDepth = $(this).data("depth");
				
				url	= "/CommentServlet";
				data = { command:command, boardId:boardId, userId:userId, cmtId:cmtId, cmtDepth:cmtDepth  };
			}
			
			
			
			console.log(data,url);
			
			if(confirm("정말 삭제하시겠습니까?")){
				callAjax(data, url);
			}
		});
	});
	
	function reCommentClose(){
		$("#addReCommentInput").remove();
	}

	
    function callAjax(data, url){
    	console.log(data, url);
    	
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
		    	
		    	console.log(resultData.code);
		    	
		    // 요청 완료 시
		    	if(resultData.code == "OK"){
		    		alert("성공.");
		    	}else{
		    		alert("실패 다시시도하세요.");		    		
		    	}
		    
		    	if(resultData.url != undefined ){
		    		location.href="/BoardServlet?command=bbsList";
		    	}else{
		    		location.reload();	
		    	}
		    	
		    },
		    error:function(request,status,error){
		    	alert("등록실패 다시시도하세요.");
			   console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
        });	
    }
</script>
	
<jsp:include page="../common/footer.jsp" flush="false"></jsp:include>

