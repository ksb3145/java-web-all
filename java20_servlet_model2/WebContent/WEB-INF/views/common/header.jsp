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


<title>상단</title>
</head>
<body>
<div class="container">

	<div class="row header">
			<div class="col-md-2">
				<a href="/loginServlet?command=home" ><h3 class="logo">BBS</h3></a>
			</div>
			<div class="col-md-9 col-md-offset-1">
				<ul class="list-inline">
					<c:choose>
						<c:when test="${userId ne null}">
							<li class="text-center"><a href="/BoardServlet?command=bbsList">게시판</a></li>
							<li class="text-center float-r"><small><a href="#">로그아웃</a></small></li>
							<li class="text-center float-r"><small><c:out value="${userId}" /> 님</small></li>
						</c:when>
						<c:otherwise>
							<li class="text-center float-r"><a href="/loginServlet?command=joinForm">회원가입</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
	</div><!-- E: row -->