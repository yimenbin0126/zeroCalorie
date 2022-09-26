window.onload = function(){
	form();
}

// 글수정 데이터 전달
function form(){

	var e_fix_form = document.e_fix_form;

	// 수정 완료 버튼 클릭
	document.querySelector('#e_btn_fix_btn').addEventListener('click', ()=>{
		e_fix_form.method="post";
		e_fix_form.action="/all/service/question-fix";
		e_fix_form.submit();
	});
	
}