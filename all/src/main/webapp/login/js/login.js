// 전체 적용
window.onload = function(){
    confirm_idpass();
};

// 아이디 비밀번호 유효성 검사 - 길이
function confirm_idpass(){
    document.querySelector("#e_login_btn").addEventListener('click', confirm_id);
	document.querySelector('#e_auto_login_check').value = "N";

    function confirm_id(){
        if(document.querySelector("#e_input_id").value.length == 0){
            alert("아이디를 입력해주세요.");
        } else {
            confirm_pass();
        }
    }

    function confirm_pass(){
        if(document.querySelector("#e_input_pass").value.length == 0){
            alert("비밀번호를 입력해주세요.");
        } else {
        	login();
        }
    }
    
    function login(){
		console.log("2");
    	if(document.querySelector('#e_auto_login').checked == true){
			document.querySelector('#e_auto_login_check').value = "Y";
		}
		console.log("1");
		var e_mainform = document.e_loginform;
	    e_mainform.method="post";
	    e_mainform.action="/all/login";
	    e_mainform.submit();
	}
}

