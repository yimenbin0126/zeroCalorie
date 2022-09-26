window.onload = function(){
	form();
}

// 글쓰기 데이터 전달
function form(){

	// 기본 선택값 초기화
	var select = $('#e_con_choice option:selected').val();
	document.querySelector('#e_choice_val').value = select;
	
	// 클릭하면 선택값 변화
	document.querySelector('#e_con_choice').addEventListener('click', ()=>{
		var select = $('#e_con_choice option:selected').val();
		document.querySelector('#e_choice_val').value = select;
	});

	// 글쓰기 버튼 클릭
	document.querySelector('#e_btn_write_btn').addEventListener('click', ()=>{
		var e_write_form = document.e_write_form;
		e_write_form.method="post";
		e_write_form.action="/all/service/question-write";
		e_write_form.submit();
	});
	
}