window.onload = function(){
    form();
};

// 폼
function form(){

    document.querySelector(".e_btn").addEventListener('click', ()=>{
		var e_nav_btn = form.e_nav_btn;
    	e_nav_btn.method = "post";
    	e_nav_btn.action = "/all/logout";
    	e_nav_btn.submit();
    });
}