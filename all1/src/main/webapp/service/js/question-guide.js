window.onload = function(){
	form();
}

// 글상세 데이터 전달
function form(){
	// 초기화
	document.querySelector('#e_bno_val').value = "none";
	// 게시물을 선택시 글상세로 넘어감
	document.querySelector('.e_boardlist').addEventListener('click', (e)=>{
		var val = e.target.parentElement.firstElementChild.value;
		document.querySelector('#e_bno_val').value = val;
		sub();
	});

	// 글 상세보기 이동
	function sub() {
		var e_bno_val_form = document.e_bno_val_form;
		e_bno_val_form.method = "get";
		e_bno_val_form.action = "/all/service/question-detail";
		e_bno_val_form.submit();
	}
	
}