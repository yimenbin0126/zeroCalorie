window.onload = function(){
	form();
}

// 뷰 데이터 전달
function form(){


	// 관리자 접속 시, 버튼 보이게 하기
	if (document.querySelector('#e_hidden_YN').value == "Y"){
		document.querySelector('#e_hidden_fix').style.display = "block";
		document.querySelector('#e_hidden_del').style.display = "block";
	} else {
		document.querySelector('#e_hidden_fix').style.display = "none";
		document.querySelector('#e_hidden_del').style.display = "none";
	}
	
	// 버튼 클릭
	if(document.querySelector('.e_hidden').style.display == "block") {

		// 글 수정 버튼
		document.querySelector('#e_btn_fix').addEventListener('click', (event)=>{
			var e_btn_fix_form = document.e_btn_fix_form;
			var con_fix = confirm('정말 글을 수정하시겠습니까?');
			if (con_fix == true) {
				e_btn_fix_form.method="post";
				e_btn_fix_form.action="/all/service/question-detail";
				e_btn_fix_form.submit();
			} else {
				event.preventDefault();
			}
		});
		
		// 글 삭제 버튼
		document.querySelector('#e_btn_delete').addEventListener('click', (event)=>{
			var e_btn_delete_form = document.e_btn_delete_form;
			var con_del = confirm('정말 글을 삭제하시겠습니까?');
			if (con_del == true) {
				e_btn_delete_form.method="post";
				e_btn_delete_form.action="/all/service/question-detail";
				e_btn_delete_form.submit();
			} else {
				event.preventDefault();
			}
		});
	}
	
}