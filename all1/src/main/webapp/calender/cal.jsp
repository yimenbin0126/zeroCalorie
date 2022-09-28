<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.*, yoo.*"
	import=" eunbin.loginjoin.MemberDAO_e, eunbin.loginjoin.MemberDTO_e,
	eunbin.service.DAO.ServiceDAO, eunbin.service.DTO.ServiceDTO, yoo.calender.*, donghyeon.* "
	
	%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
boolean mypage= (boolean)request.getAttribute("mypage");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<!-- 이미지 아이콘 관련 -->
<link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>캘린더</title>
<link rel="stylesheet" href="/all/calender/cal.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>

<script>
	window.onload=function(){
	
<%Map<String, Integer> pageDateInfo = new HashMap();

	// 날짜 정보 받아옴
	pageDateInfo = (Map) request.getAttribute("pageDateInfo");
	
	int year =(int) pageDateInfo.get("pageYear");
	int month = (int) pageDateInfo.get("pageMonth");	
	int date = (int) pageDateInfo.get("pageDate");
	
	String pageDate = year +"-"+month+"-"+date;
	
	System.out.println(  "html에서 받는 date : "+ pageDate);%>
	
	let now = new Date();
	
	// 받아온 날짜 세팅
	let year = <%=year%>;
	let month = <%=month%> ;

	// 기본 달력 날짜 그려주기
	yoo_drawCalendar(year, month);
			
	// 달력에 받아온 데이터값 넣기 
	yoo_addDataCal();

	// 이전달 버튼 눌렸을 때 
	yoo_click_pre_month(year,month);
	
	// 다음달 버튼 눌렸을 때 
	yoo_click_next_month(year,month);
	
	// 달력 안에 cell 눌렀을때
	click_cell();
	
	// 수정 버튼 눌렀을때
	update_contents();
	
	// 수정 취소 버튼 눌렸을 때
	update_contents_cancel();
	
	// 댓글 달기 버튼 눌렀을때
	click_rpl_chr_btn();
	
	// 응원메세지 댓글 입력 버튼 눌렀을 때
	click_cheerMsgRplAdd();
}

// 이전달 버튼 눌렸을 때 
function yoo_click_pre_month(year,month){
	document.querySelector("#pre_month").addEventListener("click",function() {
		
		month -= 1;
		
		// 1월[0] 이전이면 연도 줄이고, 달 12월로 세팅
		if (month < 0) {
			year -= 1;
			month = 11;
		}
										
		let clickDate = '';
		clickDate += $('#yoo_h5_year').text();
		clickDate += '-'+$('#yoo_h3_cal').text();
		console.log(clickDate);
					         
		// 날짜값 전송			        
		document.querySelector("#pageYearhidden").setAttribute("value", year);
		document.querySelector("#pageMonthhidden").setAttribute("value", month);
		document.querySelector("#pageDatehidden").setAttribute("value", <%=date%>);

		document.sendPageDateInfo.method = "post";
		document.sendPageDateInfo.action = "";
		document.sendPageDateInfo.submit();
	});
}

//다음달 버튼 눌렸을 때 
function yoo_click_next_month(year,month){
	document.querySelector("#next_month").addEventListener("click",function() {
		
		month += 1;
		// 12월([11]) 넘어가면
		if (month > 11) {
			year += 1;
			month = 0;
		}
										
		let clickDate = '';
		clickDate += $('#yoo_h5_year').text();
		clickDate += '-'+$('#yoo_h3_cal').text();
		console.log(clickDate);
					         
		// 날짜값 전송			        
		document.querySelector("#pageYearhidden").setAttribute("value", year);
		document.querySelector("#pageMonthhidden").setAttribute("value", month);
		document.querySelector("#pageDatehidden").setAttribute("value", <%=date%>);

		document.sendPageDateInfo.method = "post";
		document.sendPageDateInfo.action = "";
		document.sendPageDateInfo.submit();
	});
}
	
//달력에 받아온 데이터값 넣기 
function yoo_addDataCal() {
	
	<%List<TodoListVO> calTodolist = (ArrayList)request.getAttribute("calTodolist");%>
	
	// cursor_hand : 숫자가표시된 hover 되는 cell만 검색
	document.querySelectorAll(".cursor_hand").forEach(function(item, index){
		
	<%TodoListVO calTodoListVO = new TodoListVO();
	
		// todolist 하나씩 꺼내온다
		for(int ii =0 ; ii < calTodolist.size() ; ii++){
			calTodoListVO = (TodoListVO)calTodolist.get(ii);%>
	
			// 날짜와 = 클릭가능한 달력 index(0부터)가 같으면 내용 입력
			if(<%=calTodoListVO.getTdl_date().substring(8,11)%> == (index-1)) {
				let ctl = document.createElement("li");
				
				<%// 카테고리 색 바꾸는 부분
				String setCtgColor="green";
					if( calTodoListVO.getTdl_category().equals("운동")){setCtgColor="red"; }
					else if( calTodoListVO.getTdl_category().equals("식단")){setCtgColor="blueviolet"; }%>
				
				// 카테고리명과 컨텐츠
				let m = '<a style="color: <%=setCtgColor%>; text-decoration: none;">';
					m+= '<%=calTodoListVO.getTdl_category()%></a> ';
					m+= '<%=calTodoListVO.getTdl_contents()%>';
							
				ctl.innerHTML= m;
				
				// ul에 li로 추가
				item.querySelector("ul").appendChild(ctl);

				// title 부분
				item.setAttribute("title", item.querySelector("ul").innerText);			
			} 
		<%}%>   
	}); 
}

</script>
<script src="/all/calender/cal.js"></script>
</head>
<body>
<%
	MemberDTO_e sessionUser = new MemberDTO_e();
	sessionUser = (MemberDTO_e)session.getAttribute("user");
%>		
       	

	<div id="j_hi">
		<ul id="j_list">
			<li class=" j_menu" onclick="location.href='/all/cal/<%=sessionUser.getId()%>'">캘린더</li>
			<li class=" j_menu" onclick="location.href='/all/community/listArticles.do'">커뮤니티</li>
			<li class=" j_menu">공지사항</li>
			<li class=" j_menu" onclick="location.href='/all/service/allService'" >고객센터</li>
		</ul>

		<div id=j_nav>
			<input type='button' class="j_btn2 j_btn" 
						onclick="location.href='/all/mypage'" value="마이페이지">
			<input type='button' class="j_btn3 j_btn"
					onclick="location.href='/all/logout'"  value="로그아웃">
		</div>
	</div>

	<div id="j_wrap">
		<!-- 내용 표시 div(하얀색) -->
		<div id="j_box">
		
			<!-- 프로필, 응원메세지, 회원목록(친구찾기) div -->
			<div id="yoo_pro_chr_find_obj">
			
				<!-- 프로필 div -->
				<div id="yoo_profile_obj">
					 
					<% CalPageMbVO calPageMbVO = (CalPageMbVO)request.getAttribute("calPageMbVO"); %>
					<!-- 프로필 사진 -->
					<img class="pro_img" src="/all/download.do?fileName=<%=calPageMbVO.getPRO_IMG() %>" >
					<br>
					<!-- 닉네임 표시 -->
					<strong><%=calPageMbVO.getNICKNAME() %> </strong> 님의<br> 페이지입니다
				</div>
				
				<!-- 응원 메세지 div -->
				<div id="yoo_chr_obj">
				
					<!-- 응원메세지 입력창과 버튼 -->
					<form name="cheerMsgAdd_frm" action="" enctype="utf-8" method="post" onSubmit="return click_CheerMsgAdd(this)">
						<div id="yoo_chr_input_btn">
							<input id="yoo_chr_input" name="CHR_MSG" type="text" placeholder="응원메세지 남기기!"><input id="yoo_chr_btn" type="submit" value="입력" >
							<input type="hidden" name="command" value="cheerMsgAdd" />
							<input type="hidden" name="CHR_PARENTS_NO" value="0" />
						</div>
					</form>
							
					<!--응원메세지 출력창-->
					<div id="yoo_chr_view">
						<% List<CheerMsgVO> cheerMsglist = (ArrayList)request.getAttribute("cheerMsglist"); %>
						
						<% CheerMsgVO cheerMsgVO = new CheerMsgVO();
							int nextDepth = 1;	// 다음 댓글의 자식 유무 판단
	                    for(int i =0 ; i < cheerMsglist.size() ; i++){ %>
	                    
	                    	<% // 다음댓글의 번호가 1이면 자식이 없고(대댓), 2이면 자식이 있는것
	                    	if(i< cheerMsglist.size()-2){
	                    		CheerMsgVO nextVO = null;
	                    		nextVO = (CheerMsgVO)cheerMsglist.get(i+1);
	                    		nextDepth = nextVO.getDEPTH();
	                    	// 다음 댓글이 없으면 자식이 없으므로 지워져도 되니까 다음댓글을 1로 임의로 지정
	                    	}else{
	                    		nextDepth = 1;
	                    	}
	                    	%>
	                    	
	                    	<!-- 응원메세지 1줄 div -->
							<div class='yoo_chr_msg'>
								<% cheerMsgVO = (CheerMsgVO)cheerMsglist.get(i);%>
								<!-- DEPTH -->
								<% // 대댓글 댓글 표현을 위한 여백 
								for( int depth=0; depth<(cheerMsgVO.getDEPTH()-1)*2; depth++) {%>	
								&nbsp;
								<%} 
									if(cheerMsgVO.getDEPTH()>1){%>
									ㄴ
									<% }%>
								<!--  메세지 내용이 null이 아니면 내용 표시 -->	
								<% if(cheerMsgVO.getCHR_MSG() !=null){%>
								<!-- 글쓴이 -->
								<a href='  <%=cheerMsgVO.getID() %>' style="color: gray; text-decoration: none;"> 
									<%=cheerMsgVO.getNICKNAME() %>
								</a>
								<!-- 메세지 -->
								<%=cheerMsgVO.getCHR_MSG() %>
								<!--  메세지 내용이 null이면 삭제된 메세지로 표시 -->	
								<%}else{ %>
								삭제된 메세지 입니다
							<%} %>
							</div>
							<!-- 응원메세지 지우기 버튼 (내 페이지일 경우 또는 글쓴이)-->
							<% if(mypage || (sessionUser.getMember_no()==cheerMsgVO.getFR_MEMBER_NO()) ){ %>
								<!-- 지우기 버튼 눌렸을때 form -->
								<form method="post" action="" encType="utf-8" class="chr_del_form">
									<input class='yoo_chr_view_del_btn del_chr' type='submit' value='지우기'>
									<input type="hidden" name="CHR_NO" value="<%= cheerMsgVO.getCHR_NO()%>" />
									<input type="hidden" name="command" value="cheerMsgDel" />
									<input type="hidden" name="nowDepth" value="<%= cheerMsgVO.getDEPTH()%>" />
									<input type="hidden" name="nextDepth" value="<%= nextDepth%>" />
									
									<% 
									//지금 댓글의 원글 갯수와 정보를 보냄(parentsDapth)
									// 레벨 은 1부터 시작, 현재 내 레벨 빼기 때문에 -1
									//for( int pDapth = 0; pDapth>cheerMsgVO.getDEPTH()-1; pDapth++){
									
									// for 내 dapth-1 만큼 돌림 	
									// if 이전 댓글이 null 인지? 맞으면
										// if depth가 내 depth -1인가  맞으면  
											// chr_no 저장
										// else  : 돌필요 없음
											// for문 끝냄
									// else : 돌필요 없음
										// for문 끝냄
										
										
										
									%>	
									

										 
									<%// }%>
									
									
									
									
									
	
								</form>
							<% } %>
							<!-- 응원메세지 댓글달기 버튼 ( 대댓글은 depth 4까지만 허용)-->
							<% if(cheerMsgVO.getDEPTH()<4){%>
							<input class="chr_rpl_btn" type='button' value='댓글달기'>
							<%} %>
							<br>
							<!-- 응원 메세지 댓글 입력 -->
							<form class="chr_rpl_form" name="chr_rpl_form"  action="" enctype="utf-8" method="post" onSubmit="return click_CheerMsgAdd(this)" >
								ㄴ><input type="text" class="chl_rpl_input" name="CHR_MSG">
								<input class='rpl_chr_btn ' type='submit' value='댓글입력' >
								<input type="hidden" name="command" value="cheerMsgAdd" />
								<input type="hidden" name="CHR_PARENTS_NO" value="<%=cheerMsgVO.getCHR_NO() %>" />
							</form>
						<% } %>
					</div>

				</div>
				
				
				
				<!-- 친구찾기 div -->
				<div id="yoo_find_obj">
					<div id="yoo_find_input_btn"> 
						<!-- 멤버 전체 조회 (동현씨 부분)-->
						<form name="member" method="post" action="" id="yoo_find_input_btn_frm">
							<input id="yoo_find_input" type="text" placeholder="Id를 입력하세요"name="serchID"><input id="yoo_find_btn" type="submit" value="입력" > 
	               			<input type="hidden" name="pageYear" value="<%=year%>"/> 
							<input type="hidden" name="pageMonth" value="<%=month%>" /> 
							<input type="hidden" name="pageDate" value="<%=date%>" /> 
	               		</form>
	                </div>
	                
	                
	                <!-- 멤버 전체 조회 (동현씨 부분)-->
	               
	            	<div id="yoo_find_view">
	            		<% 
	                	List<memberVO> serchMemberlist = (ArrayList<memberVO>)new ArrayList();
							if(request.getAttribute("serchMemberlist")!=null){
								serchMemberlist = (ArrayList<memberVO>)request.getAttribute("serchMemberlist");
									for(int i=0; i<serchMemberlist.size(); i++){
										memberVO vo = new memberVO();
											vo = serchMemberlist.get(i);
						%>
	            		<p><a href='<%=vo.getId() %>' style="color: gray; text-decoration: none;"> 
                           <%=vo.getNickname() %></a>(<%=vo.getId() %>)</p>
	            		<%
									}
							}
						%>
	            	</div> 
				</div>	
				   
			</div>
			
			<hr>
			
			<!-- 달력 div -->
			<div id="yoo_cal_obj">
			
				<!-- 년, 월 표시 div -->
				<div id="disp_year_month">
				
					<!-- 연도 표시부 h5 -->
					<h5 id="yoo_h5_year"></h5>
					
					<br>
					
					<!-- 이전달 -->
					<input type="button" value=" << 이전달 " id="pre_month" class="cursor_hand">
					
					<!-- 여백 -->
					&nbsp&nbsp
					
					<!-- 월 표시 h3 -->
					<h3 id="yoo_h3_cal"></h3>
					
					<!-- 여백 -->
					&nbsp&nbsp
					
					<!-- 다음달 -->
					<input type="button" value=" 다음달 >> " id="next_month" class="cursor_hand">

					<!-- 이전달, 다음달 눌렀을때 전송되는 년,월,일 form -->
					<form name="sendPageDateInfo">
						<input type="hidden" name="pageYear" id="pageYearhidden" /> 
						<input type="hidden" name="pageMonth" id="pageMonthhidden" /> 
						<input type="hidden" name="pageDate" id="pageDatehidden" /> 
						<input type="hidden" name="tdl_date" id="clickDatehidden" />
					</form>
				</div>

				<!-- 달력 table -->
				<table id="yoo_Calendar">
				
					<thead>
						<tr>
							<th>일</th>
							<th>월</th>
							<th>화</th>
							<th>수</th>
							<th>목</th>
							<th>금</th>
							<th>토</th>
						</tr>
					</thead>
					
					<!-- 달력 숫자 표시부 cell -->
					<tbody id="yoo_tbody">
					
					<% for(int i=1; i<7; i++){ %>
						<tr id="yoo_tr_0<%=i%>">
						
						<% for(int j=0; j<7; j++){ %>
							<td class="cell"></td>
						<%} %>
					<%} %>
						
					</tbody>
				</table>
				
			</div>



			
			<!-- todolist 부분 -->
			<div class="j_todolist_wrap" >
				<!-- todolist 받아온다 -->
				<% List<TodoListVO> todoListlist = (ArrayList)request.getAttribute("todoListlist"); %>
				
				<!-- 운동 -->
				<div id="j_workout" class="j_name">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					운동
				</div>
				
				<!-- 운동 todolist list -->	
				
				<ul class = "j_ul">
				
		    		<% for(int i=0; i<todoListlist.size(); i++){ %>
		    			<c:set var="i2" value="<%=i %>"/>
		    			
		    			<!--  if 카테고리 운동 -->
		    			<c:if test = "${ todoListlist[i2].tdl_category == '운동' }" >
		    			
		    				<!-- li : tdl_contents -->
			    			<li class="j_li"><span class="mod_hidden">${todoListlist[i2].tdl_contents }</span>
			    			
			    			<!--  내 페이지라면 지우기, 수정 버튼 보이게 -->
			    			<% if(mypage){ %>
			    			
			    				<!-- 운동 todolist 지우기 form -->
				    			<form  action="" method="post" class="tdl_del_form" >
				    				<!-- 삭제 버튼 -->
					    			<button type="submit" class="button_del mod_hidden"><img src="/all/calender/img/cancel_icon.png"  class="del_icon"></button>
					    			<input type="hidden" name="tdl_no" value="${todoListlist[i2].tdl_no }"/>
					    			<input type="hidden" name="command" value="tdl_contentsDel"/>
					    			<input type="hidden" name="pageYear" value="<%=year%>"/> 
									<input type="hidden" name="pageMonth" value="<%=month%>" /> 
									<input type="hidden" name="pageDate" value="<%=date%>" /> 
				    			</form>	
						
								<!-- 운동 todolist 수정 form -->
						    	<form  action="" method="post" class= "tdl_mod_form">
						    		<input type="hidden" name="tdl_no" value="${todoListlist[i2].tdl_no }"/>
						    		<input type="hidden" name="command" value="tdl_contentsMod"/>
						    		<input type="hidden" name="tdl_category" value="운동"/>
						    		<input type="hidden" name="pageYear" value="<%=year%>"/> 
									<input type="hidden" name="pageMonth" value="<%=month%>" /> 
									<input type="hidden" name="pageDate" value="<%=date%>" /> 
									
						    		<!-- 수정하기 버튼 -->
						    		<button type="button" class="button_mod mod_hidden"><img src="/all/calender/img/edit_icon.png" class="mod_icon"></button>
						    		<input type="text" class="contents_hide contents_hide_text" name="tdl_contents" value="${todoListlist[i2].tdl_contents }"/>
                   					<!-- 수정 등록 버튼 -->
                   					<button type="submit" class="contents_hide contents_hide_btn"><img src="/all/calender/img/check_icon.png" class="modCheck_icon"></button>
						    		<!-- 수정 취소 버튼 -->
						    		<button type="button" class="contents_hide contents_hide_btn button_mod_cancle"><img src="/all/calender/img/cancel_icon.png"  class="modCancel_icon"></button>
					    		</form>	
							<% } %> 
			    			<br>
			    			</li>
		    			</c:if>
		    			
		    		<% } %>
				</ul>
				
				<!-- 내 페이지라면 todolist 등록 버튼 보이게 -->
				<% if(mypage){ %>
				<form  action="" method="post" >	
				    <input type="text" id="j_msg1" name="tdl_contents" onkeypress="" >
				    <input type="submit" id="j_app1" value="등록">
				    <input type="hidden" name="command" value="tdl_contentsAdd"/>
				    <input type="hidden" name="tdl_category" value="운동"/>
				    <input type="hidden" name="pageYear" value="<%=year%>"/> 
					<input type="hidden" name="pageMonth" value="<%=month%>" /> 
					<input type="hidden" name="pageDate" value="<%=date%>" /> 
				</form>	
				<%} %>
				
				<!--/////////////////////////////////////////// -->
				
				<!-- 식단 -->
	    		<div id="j_food" class="j_name">
		    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    		식단
	    		</div>
	    		
	    		<!-- 식단 todolist list -->		
				<ul class = "j_ul">
				
		    		<% for(int i=0; i<todoListlist.size(); i++){ %>
		    			<c:set var="i2" value="<%=i %>"/>
		    			
		    			<!--  if 카테고리 식단 -->
		    			<c:if test = "${ todoListlist[i2].tdl_category == '식단' }" >
		    			
		    				<!-- li : tdl_contents -->
			    			<li class="j_li"><span class="mod_hidden">${todoListlist[i2].tdl_contents }</span>
			    			
			    			<!--  내 페이지라면 지우기, 수정 버튼 보이게 -->
			    			<% if(mypage){ %>
			    			
			    				<!-- 식단 todolist 지우기 form -->
				    			<form  action="" method="post" class="tdl_del_form" >
				    				<!-- 삭제 버튼 -->
					    			<button type="submit" class="button_del mod_hidden"><img src="/all/calender/img/cancel_icon.png"  class="del_icon"></button>
					    			<input type="hidden" name="tdl_no" value="${todoListlist[i2].tdl_no }"/>
					    			<input type="hidden" name="command" value="tdl_contentsDel"/>
					    			<input type="hidden" name="pageYear" value="<%=year%>"/> 
									<input type="hidden" name="pageMonth" value="<%=month%>" /> 
									<input type="hidden" name="pageDate" value="<%=date%>" /> 
				    			</form>	
						
								<!-- 식단 todolist 수정 form -->
						    	<form  action="" method="post" class= "tdl_mod_form">
						    		<input type="hidden" name="tdl_no" value="${todoListlist[i2].tdl_no }"/>
						    		<input type="hidden" name="command" value="tdl_contentsMod"/>
						    		<input type="hidden" name="tdl_category" value="식단"/>
						    		<input type="hidden" name="pageYear" value="<%=year%>"/> 
									<input type="hidden" name="pageMonth" value="<%=month%>" /> 
									<input type="hidden" name="pageDate" value="<%=date%>" /> 
									
									<!-- 수정하기 버튼  -->
						    		<button type="button" class="button_mod mod_hidden"><img src="/all/calender/img/edit_icon.png" class="mod_icon"></button>
						    		<input type="text" class="contents_hide contents_hide_text" name="tdl_contents" value="${todoListlist[i2].tdl_contents }"/>
                   					<!-- 수정 등록 버튼 -->
                   					<button type="submit" class="contents_hide contents_hide_btn"><img src="/all/calender/img/check_icon.png" class="modCheck_icon"></button>
						    		<!-- 수정 취소 버튼 -->
						    		<button type="button" class="contents_hide contents_hide_btn button_mod_cancle"><img src="/all/calender/img/cancel_icon.png"  class="modCancel_icon"></button>
					    		</form>	
							<% } %> 
			    			<br>
			    			</li>
		    			</c:if>
		    			
		    		<% } %>
				</ul>
	   			
	   			<!-- 내 페이지라면 todolist 등록 버튼 보이게 -->
				<% if(mypage){ %>
				<!-- todolist 식단 등록 -->
				<form  action="" method="post" >	
				    <input type="text" id="j_msg1" name="tdl_contents" onkeypress="" >
				    <input type="submit" id="j_app1" value="등록">
				    <input type="hidden" name="command" value="tdl_contentsAdd"/>
				    <input type="hidden" name="tdl_category" value="식단"/>
				    <input type="hidden" name="pageYear" value="<%=year%>"/> 
					<input type="hidden" name="pageMonth" value="<%=month%>" /> 
					<input type="hidden" name="pageDate" value="<%=date%>" /> 
				</form>	
				<%} %>
			</div>
		</div>
	</div>

</body>
</html>