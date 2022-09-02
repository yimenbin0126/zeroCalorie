// 전체 적용
window.onload = function(){
    confirm_idpass();
};

// 아이디 비밀번호 유효성 검사 - 길이
function confirm_idpass(){
    document.querySelector("#e_login_btn").addEventListener('click', confirm_id);

    function confirm_id(){
        console.log("1")
        if(document.querySelector("#e_input_id").value.length == 0){
            alert("아이디를 입력해주세요.");
        } else {
            confirm_pass();
        }
    }

    function confirm_pass(){
        if(document.querySelector("#e_input_pass").value.length == 0){
            alert("비밀번호를 입력해주세요.");
        }
    }
}