<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<hr/>
	<div class="row">
		
	</div>
</div><!-- E: container -->

<script type="text/javascript">
<!--
	function frmSend(){
		
		if(submitCheck(document.frm)){
			var frm = document.frm;
			$("#loading").css("display","block");	// 프로그레스바..
			frm.submit();
		}
	}
-->
</script>	


<!-- 결과 메시지 -->
<c:if test="${not empty msg}">
	<script type="text/javascript">
		alert('${msg}');
	</script>
</c:if>

</body>
</html>