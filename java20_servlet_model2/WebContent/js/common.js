// 입력 값 유효성 체크
function submitCheck(frm){
	var checkVal = true;
	var pw = "";
	$("input,select,textarea",frm).each(function(e){
		
		var _this = $(this);
		var _thisVal = _this.val();
		var _checkType = _this.data("check");
		//이메일정규식
		var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		
		
		if(_checkType != ''){
			switch(_checkType){
				case "text":
					if(_thisVal == ''){
						alert(_this.attr('placeholder'));
						_this.focus();
						checkVal=false;
						return checkVal;
					}
				break;
				
				case "pw":
					if(_thisVal == ''){
						alert(_this.attr('placeholder'));
						_this.focus();
						checkVal=false;
						return checkVal;
					}else{
						pw = _thisVal;
					}
				break;
				
				case "repw":
					if(_thisVal == ''){
						alert(_this.attr('placeholder'));
						_this.focus();
						checkVal=false;
						return checkVal;
					}
					if(pw != _thisVal){
						alert("비밀번호 불일치..\n비밀번호를 확인해주세요.");
						_this.focus();
						checkVal=false;
						return checkVal;
					}
				break;
				
				case "number":
					if(_thisVal == ''){
						alert(_this.attr('placeholder'));
						_this.focus();
						checkVal=false;
						return checkVal;
					}
					if(isNaN(_thisVal)){
						alert("숫자만 입력하세요.");
						_this.focus();
						checkVal=false;
						return checkVal;
					}
				break;
				
				case "select":
					if(_thisVal == ''){
						alert(_this.data('title')+"을(를) 선택하세요.");
						_this.focus();
						checkVal=false;
						return checkVal;
					}
				break;
				
				case "email":
					if(_thisVal == ''){
						alert(_this.attr('placeholder'));
						_this.focus();
						checkVal=false;
						return checkVal;
					}
					if(_thisVal.match(regExp) == null){
						alert("이메일 형식을 확인해주세요.");
						_this.focus();
						checkVal=false;
						return checkVal;
					}
				break;
				
				case "textarea":
					if(_thisVal == ''){
						alert(_this.data('title')+"을(를) 입력하세요.");
						_this.focus();
						checkVal=false;
						return checkVal;
					}
				break;
			}
		}
	});
	
	return checkVal;
}