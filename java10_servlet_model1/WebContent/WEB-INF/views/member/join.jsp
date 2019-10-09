<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="../common/header.jsp" flush="false"></jsp:include>
	
	
	<div class="row">
		<div class="col-md-4 col-sm-2"></div>
		<div class="col-md-4 col-sm-8">
			<h4>JOIN</h4>
			
			<form action="/loginServlet" method="post">
				<input type="hidden" name="command" value="join">
				
				<div class="form-group">
					<label for="user_id">아이디</label>
					<input type="text" class="form-control" id="userId" name="userId" value="${defaultId}" placeholder="아이디를 입력하세요." />
				</div>
				<div class="form-group">
					<label for="user_pw">비밀번호</label>
					<input type="password" class="form-control" id="userPw" name="userPw" placeholder="비밀번호를 입력하세요." />
				</div>
				<div class="form-group">
					<label for="user_pw">비밀번호 확인</label>
					<input type="password" class="form-control" id="userRePw" name="userRePw" placeholder="비밀번호 확인을 입력하세요." />
				</div>
				<div class="form-group">
					<label for="user_pw">이름</label>
					<input type="text" class="form-control" id="userName" name="userName" placeholder="이름을 입력하세요." />
				</div>
				<div class="form-group">
					<label for="user_pw">이메일</label>
					<input type="text" class="form-control" id="userEmail" name="userEmail" placeholder="이메일주소를 입력하세요." />
				</div>
				<div class="col-md-12 text-center">
					<button type="submit" class="btn btn-primary">가입</button>
					<a href="/loginServlet?command=home" class="btn btn-link">취소</a>
				</div>
				
			</form>
			
		</div>
		<div class="col-md-4 col-sm-2"></div>
	</div>
	
	
<jsp:include page="../common/footer.jsp" flush="false"></jsp:include>

