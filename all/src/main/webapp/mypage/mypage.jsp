<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="jisoo.MemberDAO" %>
<%@page import="jisoo.MemberVO" %>
<%@page import="java.util.*" %>
<%@page import= "eunbin.loginjoin.MemberDTO_e,eunbin.loginjoin.MemberDAO_e"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%	// 지수씨
	MemberDAO_e dao = new MemberDAO_e();
%>

 <%
	// 소연
	MemberDTO_e sessionUserDTO = new MemberDTO_e();
	sessionUserDTO = (MemberDTO_e)session.getAttribute("user");
 %>

<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인페이지</title>
    <link rel="stylesheet" href="mypage.css">
    
    <style>
    #mod_weight{
    	display: none;
    }
    #mod_msg{
    	color: red;
    	font-size: 12px;
    }
    #yoo_cur_btn{
    border: 0px;
    color: white;
    background-color: black;
    width: 50px;
    height: 30px;
    background-color: rgb(39, 56, 104);
}
    </style>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.js">// jquery로딩완료</script>
	
<script>
    window.onload=function(){
    	yoo_bind();
    }

	function yoo_bind() {
		let option = {
			url: "/all/yoo2",
			type: "get",
			dataType: 'json',
			data: {"MEMBER_NO": <%=sessionUserDTO.getMember_no()%>},
			success: function (data) {
				try {
					console.log(data);
					yoo_drawChart(data);
					drawSelect(data);
					document.querySelector("#yoo_select_date").addEventListener("change", function (item) {
						console.log("value ::::", document.querySelector("select option:checked").value);
						let index = document.querySelector("select option:checked").value;
						yoo_drawChart(data, data.length - index);
					});
				} catch (err) {
					console.log("ERR", err);
				}
			},
			error: function (err) { 
				console.log("ERR view click", err);
			},
			complete: function (data) {
				console.log("완료", data);
			}
		}
		$.ajax(option);
		return 1;
	}

	function yoo_drawChart(data, date_count = data.length) {
		console.log("date_count", date_count);
		let CURRENT_WEIGHT = [];
		let TARGET_WEIGHT = [];
		let WEIGHT_DATE = [];
		
		// 오늘 날짜 생성
		let dateInfo = dateInfo_fn();

		

		let j = 0;
		for (let i =data.length - date_count; i < data.length; i++) {
			console.log("j : ", j, ", i : ", i,", data.length : ",data.length, ", date_count : ",date_count );

				console.log("data[i].MEMBER_NO");
				console.log(data[i].MEMBER_NO);
				CURRENT_WEIGHT[j] = data[i].CURRENT_WEIGHT;
				TARGET_WEIGHT[j] = data[i].TARGET_WEIGHT;
				WEIGHT_DATE[j] = data[i].WEIGHT_DATE;

			// (입력)직전 목표 표시 부분
			document.querySelector("#yoo_TARGET_WEIGHT_input").setAttribute("placeholder", "직전 목표 "+ TARGET_WEIGHT[j]);
			// 값 임시저장
			document.querySelector("#TARGET_WEIGHT2").setAttribute("value", TARGET_WEIGHT[j]);
			
			// (수정)직전 목표 표시 부분
			document.querySelector("#yoo_CURRENT_WEIGHT_input_mod").setAttribute("placeholder",CURRENT_WEIGHT[j]);
			document.querySelector("#yoo_TARGET_WEIGHT_input_mod").setAttribute("placeholder",TARGET_WEIGHT[j]);
			// 값 임시저장
			document.querySelector("#yoo_CURRENT_WEIGHT_input_mod2").setAttribute("value", CURRENT_WEIGHT[j]);
			document.querySelector("#yoo_TARGET_WEIGHT_input_mod2").setAttribute("value", TARGET_WEIGHT[j]);
			
			// ajax에서 가져온 값중에 오늘 날짜의 값이 있으면 입력창이 수정창으로 바뀜
			if(WEIGHT_DATE[j]==dateInfo){
				input_to_mod();
			}
			j++;
		}

		let chartStatus = Chart.getChart("yoo_canvas");
		if (chartStatus != undefined) {
			chartStatus.destroy();
		}

		let ctx = document.getElementById('yoo_canvas').getContext('2d');
		let mixedChart = new Chart(ctx, {
			data: {
				datasets: [
					{
						type: 'bar',
						label: 'today 몸무게',
						data: CURRENT_WEIGHT,
						backgroundColor: [
							//색상
							'rgba(54, 79, 145, 0.2)',
							'rgba(55, 109, 198, 0.2)',
							'rgba(7, 147, 233, 0.2)',
							'rgba(55, 99, 200, 0.2)',
							'rgba(54, 79, 145, 0.2)',
							'rgba(7, 147, 233, 0.2)',
							'rgba(80, 120, 216, 0.2)'],
						borderColor: [
							//경계선 색상
							'rgba(50, 66, 118, 1)', 'rgba(50, 66, 118, 1)',
							'rgba(50, 66, 118, 1)',
							'rgba(50, 66, 118, 1)',
							'rgba(50, 66, 118, 1)',
							'rgba(50, 66, 118, 1)'],
						borderWidth: 1
					}, {
						type: 'line',
						label: '목표 몸무게',
						data: TARGET_WEIGHT,
						backgroundColor: ['rgba(63, 72, 204, 0.9)'],
						borderColor: ['rgba(63, 72, 204, 0.9)'],
						borderWidth: 2,
					}],
				labels: WEIGHT_DATE
			},
		});
	}
	function drawSelect(data) {
		let WEIGHT_DATE = [];
		let html = "";
		$(data).each(function (index, item) { // 항목 추가
			html += '<option class="yoo_select_date" value="' + index + '">' + item.WEIGHT_DATE + '</option>';
		});
		$("#yoo_select_date").append($(html));

	}
	// 몸무게 입력 클릭했을때
	function click_add_weight(){
		if(document.querySelector("#yoo_CURRENT_WEIGHT_input").value==""){
			alert(' 현재 몸무게 값은 필수입니다. ');
		}else{
			
			if(document.querySelector("#yoo_TARGET_WEIGHT_input").value ==""){

				document.querySelector("#yoo_TARGET_WEIGHT_input").setAttribute("value",document.querySelector("#TARGET_WEIGHT2").value);
			}

			
			document.add_weight.method = "post";
			document.add_weight.action = "../mypage";
			document.add_weight.submit(); 
			
		}
		
			
	}
	// 몸무게 수정 클릭했을때
	function click_mod_weight(){
		if(document.querySelector("#yoo_CURRENT_WEIGHT_input_mod").value==""){
			document.querySelector("#yoo_CURRENT_WEIGHT_input_mod").setAttribute("value",document.querySelector("#yoo_CURRENT_WEIGHT_input_mod2").value);
		}
		if(document.querySelector("#yoo_TARGET_WEIGHT_input_mod").value ==""){
			document.querySelector("#yoo_TARGET_WEIGHT_input_mod").setAttribute("value",document.querySelector("#yoo_TARGET_WEIGHT_input_mod2").value);
		}

			document.mod_weight.method = "post";
			document.mod_weight.action = "../mypage";
			document.mod_weight.submit(); 
			
		}
		

	function dateInfo_fn() {
<%
		GregorianCalendar now = new GregorianCalendar();
		String dateInfo = String.format("%d-%02d-%02d", now.get(1), now.get(2)+1, now.get(5));
		
%>
		// 오늘 날짜 생성
		dateInfo = '<%=dateInfo%>';
		return dateInfo;
	}	
	
	function input_to_mod(){
		$("#mod_weight").show();
		$("#add_weight").hide();
		
	}
</script>


</head>
<body>
   <!-- 헤더 시작 -->
	<div id="j_hi">
		<!-- <img src="./img/logo.png" id="j_logo"> -->
		<%
                // 데이터 불러오기 위한 선언
                MemberDTO_e m_dto = new MemberDTO_e();
                MemberDAO_e m_dao = new MemberDAO_e();
                m_dto = (MemberDTO_e)session.getAttribute("user");
        %>
		<ul id="j_list">
			<li class=" j_menu"
				onclick="location.href='/all/cal/<%=m_dto.getId()%>'">캘린더</li>
			<li class=" j_menu"
				onclick="location.href='/all/community/listArticles.do'">커뮤니티</li>
			<li class=" j_menu">공지사항</li>
			<li class=" j_menu" onclick="location.href='/all/service/allService'">고객센터</li>
		</ul>

		<div id=j_nav>
			<input type='button' class="j_btn2 j_btn"
				onclick="location.href='/all/mypage'" value="마이페이지"> <input
				type='button' class="j_btn3 j_btn" onclick="location.href='/all/logout'"
				value="로그아웃">
		</div>
	</div>
	<!-- 헤더 끝 -->
    
    <div id="j_wrap">
        <div id="j_box">
        
        	<!--//////////// 지수씨 파트 //////////////-->
			<div id="j_sec1">

			<%
				// 로그인 유무
	            m_dto = (MemberDTO_e)session.getAttribute("user");
			%>
				<div class="k_back">
					<form action="upload.do" method="post" enctype="multipart/form-data">

						<h1><%=m_dto.getNickname()%>님의 프로필</h1>
						
						<div class ="profile">
							<img class="k_profile" src="img/default_profile.jpg"><br>
							<input class="k_change_profile" type="file" accept="image/*" name="profileImg"><br>
							<input type="hidden" name="memberId" id="memberId" value="<%=m_dto.getNickname()%>" /> 							<input class="k_saveimg" type="submit" value="사진 저장">
						</div><!-- 얘 묶어 -->
					</form>
					<div class="profile_detail">
							<div class="k_t_box">
								<div class="k_inline1">이름<%--<span class="k_tp_letter">두글</span> --%>
									<input type="text" value="<%=m_dto.getName()%>" readonly="readonly">
								</div>
								<div class="k_inline2">닉네임<%--<span class="k_tp_letter">한</span> --%>
									<input type="text" value="<%=m_dto.getNickname()%> " readonly="readonly">
								</div>
							</div><br>
							
							<!-- cm, kg등 기준은 이미지로 text박스 오른쪽에 -->
							<div class="k_t_box">
								<div class="k_inline1">&nbsp;키&nbsp;&nbsp;<%--<span class="k_tp_letter">세글자</span> --%>
									<input type="text" value="<%=m_dto.getHeight()%>" readonly="readonly">
								</div>
								<div class="k_inline2">&nbsp;성별&nbsp;&nbsp;<%--<span class="k_tp_letter">두글</span> --%>
									<input type="text" value="<%=m_dto.getGender()%>" readonly="readonly"><br>
								</div>
							</div>
							
							<div class="k_t_box2">
								생년월일<input type="text" value="<%=m_dto.getBirth() %>" readonly="readonly">>
							</div>
							<div class="k_t_box2">
								이메일&nbsp;&nbsp;<%--<span class="k_tp_letter">한</span> --%>
								<input type="text" value="<%=m_dto.getEmail()%>" readonly="readonly">>
							</div>
							<div class="k_t_box2">
								전화번호<input type="text" value="<%=m_dto.getTel()%>" readonly="readonly">>
							</div>
						</div>
							
					
							<div class="edit_all">
							<form method="get" action="/all/mypage/editMemberForm">
								<input type="hidden" name="id" value="<%=m_dto.getId()%>">
								<input class="k_btn_edit" type="submit" value="편집하기">
							</form>
							</div>
				</div>
		</div>

            
            <!-- 소연씨 파트 -->
            <div id="yoo_div">
            	
           
	            <h3>내 몸무게 보기</h3> 
	            
				<div id="yoo_weight_input">
					<form id="add_weight"  name ="add_weight">
						오늘의 몸무게 : <input id="yoo_CURRENT_WEIGHT_input" name="CURRENT_WEIGHT" type="text" placeholder="몸무게를 입력해주세요">
						kg, 
						목표 몸무게: <input id="yoo_TARGET_WEIGHT_input" name="TARGET_WEIGHT" type="text" placeholder="목표 몸무게를 입력해주세요"> kg 
						<input id="yoo_cur_btn" type="button" value="입력" onclick="click_add_weight()">
						<input type="hidden" name="command" value="weightAdd" />
						<input type="hidden" name="TARGET_WEIGHT2" id="TARGET_WEIGHT2" />
					</form>
					
					<form id="mod_weight" name ="mod_weight">
						오늘의 몸무게 : <input id="yoo_CURRENT_WEIGHT_input_mod" name="CURRENT_WEIGHT" type="text" placeholder="몸무게를 입력해주세요">
						kg, 
						목표 몸무게: <input id="yoo_TARGET_WEIGHT_input_mod" name="TARGET_WEIGHT" type="text" placeholder="목표 몸무게를 입력해주세요"> kg 
						<input id="yoo_cur_btn" type="button" value="수정" onclick="click_mod_weight()">
						<br><span id=mod_msg>오늘은 이미 몸무게를 입력 하셨습니다. 수정만 가능합니다.</span>
						<input type="hidden" name="command" value="weightMod" />
						<input type="hidden"  id="yoo_CURRENT_WEIGHT_input_mod2" />
						<input type="hidden"  id="yoo_TARGET_WEIGHT_input_mod2" />
					</form>
				</div>
				
				<br>
	            <select id="yoo_select_date"></select>
				<canvas id="yoo_canvas"></canvas>
			</div>
        </div>
	</div>

</body>
</html>