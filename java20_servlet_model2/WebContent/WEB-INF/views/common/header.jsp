<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Language" content="ko">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" initial-scale="1">

<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/bootstrap.css">
<!-- 스크립트 선언 -->
<script src="js/jquery-1.11.2.min.js"></script>
<script src="js/common.js"></script>
<script src="js/bootstrap.min.js"></script>

<title>게시판</title>
</head>
<body>

<div id="loading">
	<div class="loadingInner">
		<img src="/img/loading.gif">
		<p>잠시만 기다려주세요...</p>
	</div>
</div>

<div class="container">
	<div class="row header">
			<div class="col-md-2">
				<a href="/loginServlet?command=home" ><h3 class="logo">BBS</h3></a>
			</div>
			<div class="col-md-9 col-md-offset-1">
				<ul class="list-inline">
					<c:choose>
						<c:when test="${sessionVO.userId ne null}">
							<li class="text-center"><a href="/BoardServlet?command=bbsList">게시판</a></li>
							<li class="text-center float-r"><small><a href="/loginServlet?command=logout">로그아웃</a></small></li>
							<li class="text-center float-r"><small><c:out value="${sessionVO.userId}" /> 님</small></li>
						</c:when>
						<c:otherwise>
							<li class="text-center float-r"><a href="/loginServlet?command=joinForm">회원가입</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
	</div><!-- E: row -->