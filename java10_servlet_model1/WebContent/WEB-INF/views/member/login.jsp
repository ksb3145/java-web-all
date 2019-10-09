<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="../common/header.jsp" flush="false"></jsp:include>
	
	
	<div class="row">
		<div class="col-md-4 col-sm-2"></div>
		<div class="col-md-4 col-sm-8">
			<h4>LOGIN</h4>
			
			<form action="/loginServlet" method="post">
				<input type="hidden" name="command" value="login">
				
				<div class="form-group">
					<label for="user_id">아이디</label>
					<input type="text" class="form-control" id="userId" name="userId" value="" placeholder="아이디를 입력하세요." />
				</div>
				<div class="form-group">
					<label for="user_pw">비밀번호</label>
					<input type="password" class="form-control" id="userPw" name="userPw" value="" placeholder="비밀번호를 입력하세요." />
				</div>
				
				<div class="col-md-12 text-center">
					<button type="submit" class="btn btn-primary">로그인</button>
				</div>
				
			</form>
			
		</div>
		<div class="col-md-4 col-sm-2"></div>
	</div>
	
	
<jsp:include page="../common/footer.jsp" flush="false"></jsp:include>

