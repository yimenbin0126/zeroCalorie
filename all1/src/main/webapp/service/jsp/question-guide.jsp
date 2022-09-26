<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="eunbin.loginjoin.MemberDAO_e,eunbin.loginjoin.MemberDTO_e,eunbin.service.DAO.ServiceDAO,eunbin.service.DTO.ServiceDTO,java.util.List,java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객센터</title>
<link rel="stylesheet" href="./css/header.css">
<link rel="stylesheet" href="./css/question-guide.css">
<script src="./js/question-guide.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>

<body>
	
	<!-- 헤더 시작 -->
    <div id="j_hi">
        <!-- <img src="./img/logo.png" id="j_logo"> -->

		<%
		// 데이터 불러오기 위한 선언
		MemberDTO_e m_dto = new MemberDTO_e();
		MemberDAO_e m_dao = new MemberDAO_e();

		// 로그인 유무
		if ((MemberDTO_e) session.getAttribute("user") != null) {
			m_dto = (MemberDTO_e) session.getAttribute("user");
		%>
		<ul id="j_list">
            <li class="j_menu1 j_menu" onclick="location.href='/all/cal/<%=m_dto.getId()%>'">캘린더</li>
            <li class="j_menu2 j_menu" onclick="location.href='/all/community/listArticles.do'">커뮤니티</li>
            <li class="j_menu3 j_menu">공지사항</li>
            <li class="j_menu5 j_menu" onclick="location.href='/all/service/allService'">고객센터</li>
        </ul>
        <div id = e_nav>
        	<div id="e_welcome">
        		<%=m_dto.getNickname()%>님 환영합니다.
        	</div>
        	<form name="e_nav_btn">
	        	<input type ="hidden" name="e_logout" value="Y">                   
        	</form>
        	<!-- null 오류 방지용 시작 -->
        	<input type ='hidden' class = "j_btn1 j_btn" onclick="location.href='/all/login'" value="로그인">
            <input type ='hidden' class = "j_btn2 j_btn" onclick="location.href='/all/join'" value="회원가입">
            <!-- null 오류 방지용 끝 -->
            <!-- 나타나는 부분 시작 -->
            <input type ='button' class = "e_btn e_btn" onclick="location.href='/all/logout'" value="로그아웃">
            <input type ='button' class = "e_btn2 e_btn" onclick="location.href='/all/mypage'" value="마이페이지">
            <!-- 나타나는 부분 끝 -->
        </div>
        <%
        	} else {
        %>
        <ul id="j_list">
            <li class="j_menu1 j_menu" onclick="location.href='/all/cal/<%=m_dto.getId()%>'">캘린더</li>
            <li class="j_menu2 j_menu" onclick="location.href='/all/community/listArticles.do'">커뮤니티</li>
            <li class="j_menu3 j_menu">공지사항</li>
            <li class="j_menu5 j_menu" onclick="location.href='/all/service/allService'">고객센터</li>
        </ul>
        <div id = j_nav>
        	<!-- null 오류 방지용 시작 -->
            <input type ='hidden' class = "e_btn e_btn" onclick="location.href='/all/logout'" value="로그아웃">
            <input type ='hidden' class = "e_btn2 e_btn" onclick="location.href='/all/mypage'" value="마이페이지">               
            <!-- null 오류 방지용 끝 -->
            <!-- 나타나는 부분 시작 -->
            <input type ='button' class = "j_btn1 j_btn" onclick="location.href='/all/login'" value="로그인">
            <input type ='button' class = "j_btn2 j_btn" onclick="location.href='/all/join'" value="회원가입">
            <!-- 나타나는 부분 끝 -->
        </div>
        <%
        	}
        %>
    </div>
    <!-- 헤더 끝 -->
    
    <%
        // 데이터 불러오기 위한 선언 (추가)
        		ServiceDAO s_dao = new ServiceDAO();
        		List<ServiceDTO> s_dto_list = new ArrayList<ServiceDTO>();
        %>

	<section>
		<div id="j_wrap">
			<div id="j_box">

				<div class="e_div">
					<!-- 왼쪽 카데고리 -->
					<nav class="e_nav">
						<!-- 문의 전체보기 -->
						<div class="e_nav_all" onclick="location.href='/all/service/allService'">
							문의 전체보기
						</div>
						<!-- 자주하는 질문 -->
						<div class="e_nav_question" onclick="location.href='/all/service/question-member'">
							<div class="e_que_div">자주하는 질문</div>
							<div><img src="./img/category_click.png"></div>
						</div>
						<!-- 1:1 문의 -->
						<div class="e_nav_onebyone" onclick="location.href='/all/service/oneByone'">1:1 문의</div>
					</nav>

					<!-- 오른쪽 내용 -->
					<div class="e_right">
						<!-- 상단 -->
						<div class="e_hd_top">고객센터 &gt; 자주하는 질문</div>
						<div class="e_header">
							<div class="e_hd_top_que">자주하는 질문</div>
							<div class="e_hd_top_con">
								<span> 설명설명설명설명설명설명<br> 설명설명설명설명설명설명<br>
									설명설명설명설명설명설명
								</span>
							</div>
							
							<!-- 글쓰기 버튼 보이기 (로그인 + 관리자일 경우) -->
							<%
							// 로그인 여부
													if((MemberDTO_e)session.getAttribute("user") !=null){
														// 관리자 여부
														String id = m_dto.getId();
														if(s_dao.board_admin_type(id).equals("Y")){
							%>
							<form name="e_hd_top_write_form">
								<input type="button"  value="글쓰기" class="e_hd_top_write"
								onclick="location.href='/all/service/question-write'">							
							</form>
							<%
									}
								}
							%>

							<!-- 카데고리 선택 -->
							<div class="e_hd_choice">
								<form name="e_hd_choice_form">
									<input type="hidden" name="e_hd_choice_LR" id="e_hd_choice_LR" value="L">
								</form>
								<div id="e_choice_mem" onclick="location.href='/all/service/question-member'">회원 정보 관리</div>
								<div id="e_choice_guide">사이트 이용 가이드</div>
							</div>
						</div>

						<!-- 게시물 불러오기 - 사이트 이용 가이드 -->
						<div class="e_content">
							<!-- 게시물 번호 보내기 - 상세보기 -->
							<form name="e_bno_val_form" id="e_bno_val_form">
								<input type="hidden" name="e_bno_val" id="e_bno_val">
							</form>
							<div id="e_con_guide">
								<ul>
									<li>번호</li>
									<li>제목</li>
									<li>글쓴이</li>
									<li>작성시간</li>
									<li>좋아요</li>
								</ul>
								<%
									// 게시물들 불러오기
									if((ArrayList<ServiceDTO>)request.getAttribute("s_dto_list")!=null
										&& ((ArrayList<ServiceDTO>)request.getAttribute("s_dto_list")).size()!=0){
										s_dto_list = (ArrayList<ServiceDTO>)request.getAttribute("s_dto_list");
										int j = s_dto_list.size()+1;
										for(int i=0; i<s_dto_list.size(); i++){
											j--;
											ServiceDTO s_dto = new ServiceDTO();
											s_dto = s_dto_list.get(i);
								%>
								<ul class="e_boardlist">
									<li value="<%=s_dto.getBno()%>"><%=j%></li>
									<li><%=s_dto.getTitle()%></li>
									<li><%=s_dto.getNickname()%></li>
									<li><%=s_dto.getCreate_time()%></li>
									<li><%=s_dto.getHeart()%></li>
								</ul>
								<%
										}
									}
								%>
							</div>
						</div>

						<div class="e_paging">
							<div class="e_paging_btnleft">&lt;</div>
							<div class="e_paging_num">
								<div id="bno1">1</div>
							</div>
							<div class="e_paging_btnright">&gt;</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</section>

</body>
</html>