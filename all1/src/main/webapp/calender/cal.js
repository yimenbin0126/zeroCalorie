
// 기본 달력 날짜 그려주기
function yoo_drawCalendar(year, month) {
	
	// 시작요일 
    let now = new Date();
    let monthStartDay = new Date(year, month, 1);
    let start_day = monthStartDay.getDay();
    
    // 월 날짜 갯수
    let month_count = new Date(year,month+1, 0);
    month_count= month_count.getDate();
    
    console.log("::::시작요일 : ", start_day, ", 월 날짜 갯수 : ", month_count);
    
    let j=1; // 주
    let k=1;
    let m = month_count+1;
    //let m = (month[now.getMonth()])+1   // 이번달의 날짜 수

    // 이전에 그렸던거 지우고 시작함 
    $(".cell").empty();
    
    // 이전에 셀 속성 손모양 지우고 시작함 
	document.querySelectorAll(".cell").forEach(function(item, index){
		item.classList.remove("cursor_hand");
    });
    
    // 현재 달 표시
    document.querySelector("#yoo_h3_cal").innerHTML=(month+1);
    document.querySelector("#yoo_h5_year").innerHTML=year;
    
    //첫주
    for(let i=start_day; i <7; i++){
       
        let h_date = document.createElement("h3");
        h_date.setAttribute("class", "yoo_td_h3");
        h_date.innerHTML=k;
        
        // 날짜 있는칸만 손모양 주고 싶어서 속성 추가해줌
        document.getElementsByTagName("tr")[j].getElementsByTagName("td")[i].classList.add("cursor_hand");
        // 날짜 그려줌
        document.getElementsByTagName("tr")[j].getElementsByTagName("td")[i].appendChild(h_date);
        // 속성 추가해줌
        document.getElementsByTagName("tr")[j].getElementsByTagName("td")[i].setAttribute("data-calnum", k);
        // 내용물 쓸 ul 추가해줌
        document.getElementsByTagName("tr")[j].getElementsByTagName("td")[i].appendChild(document.createElement("ul"));
        k++;
	}
		
	// 6th줄 지울건지 계산
	if(((month_count-k)/7)<4){
		console.log("ksdjfl");
		document.querySelector("#yoo_tr_06").classList.add("hide");
	}else{
		console.log("여기");
		document.querySelector("#yoo_tr_06").classList.remove("hide");
	}
      
	// 월[이번달]+1  
    while(k<m){ 
        j++;
        for( let i=0; i <7; i++ ){
			// tr의 td 들 안에 <h3> 생성
            let h_date = document.createElement("h3"); 
            h_date.setAttribute("class", "yoo_td_h3");
            h_date.innerHTML=k;

			// 날짜 있는칸만 손모양 주고 싶어서 속성 추가해줌
        	document.getElementsByTagName("tr")[j].getElementsByTagName("td")[i].classList.add("cursor_hand");
        	// 날짜 그려줌
        	document.getElementsByTagName("tr")[j].getElementsByTagName("td")[i].appendChild(h_date);
        	// 속성 추가해줌
        	document.getElementsByTagName("tr")[j].getElementsByTagName("td")[i].setAttribute("data-calnum", k);
        	// 내용물 쓸 ul 추가해줌
        	document.getElementsByTagName("tr")[j].getElementsByTagName("td")[i].appendChild(document.createElement("ul"));
        	k++;
        
            if(!(k<m)){
				break;
			}
        }
        
        if(!(k<m)){
			break;
        }
	}
}

 // 응원메세지 표시 함수
/*function fn_yoo_chr_view(){   
	document.querySelector("#yoo_chr_btn").addEventListener("click",function(){
	let val = document.querySelector("#yoo_chr_input").value;

	let ms = document.querySelector("#yoo_chr_view");
	//let html="";                  // 1줄만 나옴
	let html=ms.innerHTML;       // 여러줄 하고싶을때 (but 전체 다시 그리는 비효율적 방법)
		html +="<div class='yoo_chr_msg'>";
		html +="        "+ val;
		html +="</div><input class='yoo_chr_view_del_btn' type='submit' value='지우기'><br>";
		ms.innerHTML = html; // .innerHTML : 덮어쓰기 기능
	});
}*/

// 달력 안에 cell 눌렀을때
function click_cell(){
	// td가 눌렸을때 내용물이 존재하면 2022-11-16 형식으로 돌려준다
	$("td").off("click").on("click",function(){
		console.log( $(this).text() ); // day
	    
		if($(this).attr("data-calnum")){
			let clickDate = '';
			clickDate += $('#yoo_h5_year').text();
			clickDate += '-'+$('#yoo_h3_cal').text();
			clickDate += '-'+$(this).attr("data-calnum");
			console.log(clickDate);	
		
			document.querySelector("#clickDatehidden").setAttribute("value", clickDate);	//숫자 눌렀을때
			document.querySelector("#pageYearhidden").setAttribute("value", $('#yoo_h5_year').text()); // year
			document.querySelector("#pageMonthhidden").setAttribute("value", ($('#yoo_h3_cal').text()-1)); // month
			document.querySelector("#pageDatehidden").setAttribute("value", ($(this).attr("data-calnum"))); // date
			
			document.sendPageDateInfo.method = "post";
			document.sendPageDateInfo.action = "";
			document.sendPageDateInfo.submit();
		}
	});
}

// 수정 버튼 눌렀을때
function update_contents(){
	$(".button_mod").off("click").on("click",function(){
	
		// 기존컨텐츠와 수정버튼 숨김 
		$(this).parent().parent().find(".mod_hidden").hide();
		// 입력창과 수정등록과 수정취소 버튼 보임
		$(this).parent().find(".contents_hide").show();

	});	
}

// 수정 취소 버튼 눌렸을 때
function update_contents_cancel(){
	$(".button_mod_cancle").off("click").on("click",function(){
      
      	// 기존컨텐츠와 수정버튼 보임
		$(this).parent().parent().find(".mod_hidden").show();
		// 입력창과 수정등록과 수정취소 버튼 숨김
		$(this).parent().find(".contents_hide").hide();
      
	});
}

function clickLogout(){
	location.href='/all/logout';
}

