<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="../common/header.jsp" flush="false"></jsp:include>
	
	
	<div class="row">
	
		<div class="col-md-12">
			<h4>글쓰기</h4>
			
			<form name="frm" id="frm" class="form-horizontal" action="/BoardServlet" method="post" enctype="multipart/form-data">

				<c:choose>
					<c:when test="${not empty boardDetail.id && reqFrm ne 'insert'}" >
						<input type="hidden" id="command" name="command" value="bbsUpdate" />
						<input type="hidden" id="reqFrm" name="reqFrm" value="${'bbsUpdateForm' eq reqFrm ? 'bbsUpdate' : 'bbsReplyUpdate'}" />
						<input type="hidden" id="boardId" name="boardId" value="${boardDetail.id}" />
						<input type="hidden" id="userId" name="userId" value="${boardDetail.userId}" />
						<input type="hidden" id="page" name="page" value="${page}" />
					</c:when>
					<c:otherwise>
						<input type="hidden" id="command" name="command" value="bbsInsert" />
						<input type="hidden" id="page" name="page" value="${page}" />
						<input type="hidden" id="reqFrm" name="reqFrm" value="${not empty reqFrm ? 'bbsReplyInsert' : ''}" />
						<input type="hidden" id="userId" name="userId" value="${sessionVO.userId}" />
						<input type="hidden" id="boardId" name="boardId" value="${boardId}" />
						<input type="hidden" id="bGroup" name="bGroup" value="${0 eq boardDetail.bGroup ? boardDetail.id : boardDetail.bGroup}" />
					</c:otherwise>
				</c:choose>
				
				
				<div class="form-group">
					<label for="title">제목</label>
					<input type="text" class="form-control" id="title" name="title" value="${reqFrm eq 'insert' ? reTitle : boardDetail.title}" data-check="text"  placeholder="제목을 입력하세요." />
				</div>
				
				<div class="form-group">
					<label for="content">내용</label>
					<textarea class="form-control" rows="3"  id="content" data-check="textarea" data-title="내용을 입력하세요." name="content">${reqFrm eq 'insert' ?  null : boardDetail.content}</textarea>
				</div>
				
				<div class="form-group">
					
					
					<c:choose>
						<c:when test="${not empty boardFiles && reqFrm ne 'insert'}" >
							<c:forEach var="obj" items="${boardFiles}" varStatus="status" >
								<ul>
									<li>
										${obj.fileOrgName}
										<button type="button" class="btn lg red delBtn" data-command="FileDel" data-fileId="${obj.fId}">삭제</button> 
									</li>
								</ul>
							</c:forEach>
							<input type="hidden" id="fileUploadYN" name="fileUploadYN" value="N" />
						</c:when>
						<c:otherwise>
							<label for="file-upload">파일업로드</label>
							<input type="file" class="form-control" id="fileUpload" name="fileUpload" />
							<input type="hidden" id="fileUploadYN" name="fileUploadYN" value="Y" />
						</c:otherwise>
					</c:choose>
					
						
					
				</div>
				
				<div class="col-md-12">
					<ul class="list-inline">
						<li>
							<a href="/BoardServlet?command=bbsList" class="btn btn-primary">목록</a>
						</li>
						<li class="float-r">
							<c:choose>
								<c:when test="${not empty boardDetail.id && sessionVO.userId ne null && reqFrm ne 'insert'}" >
									<button type="submit" class="btn btn-warning">수정</button>
								</c:when>
								<c:otherwise>
									<button type="button" onClick="frmSend();" class="btn btn-success">등록</button>
								</c:otherwise>
							</c:choose>
						</li>
					</ul>
				</div>
				
			</form>
		</div>
		
		
		
	</div>
<script type="text/javascript">
<!--
	$(document).ready(function() {
		//파일 삭제
		$(".delBtn").on("click",function(e){
			var data = "";
			var command = $(this).attr("data-command");
			var fileId = $(this).attr("data-fileId");
			url = "/BoardServlet";
			data = { command:command, fileId:fileId };
			
			if(confirm("정말 삭제하시겠습니까?")){
				callAjax(data, url);
			}
		});
	});
	
	
	function callAjax(data, url){
		//console.log(data, url);
		
		$.ajax({
			url:url,
		    async:true,
		    type:'POST',
		    data: data,
		    dataType:'json',
		    beforeSend:function(jqXHR) {
		    	$("#loading").css("display","block");
		    },
		    success:function(resultData) {
		    	//console.log(resultData.code);
		    	$("#loading").css("display","none");
		    	if(resultData.code != "OK"){
		    		alert("실패 다시시도하세요.");		    		
		    	}
				location.reload();
		    },
		    error:function(request,status,error){
		    	alert("등록실패 다시시도하세요.");
			   console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
	    });	
	}
-->
</script>
<jsp:include page="../common/footer.jsp" flush="false"></jsp:include>

