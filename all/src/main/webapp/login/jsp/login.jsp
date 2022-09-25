<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="eunbin.loginjoin.MemberDAO_e,eunbin.loginjoin.MemberDTO_e,eunbin.service.DAO.ServiceDAO,eunbin.service.DTO.ServiceDTO" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link rel="stylesheet" href="login/css/header.css">
    <link rel="stylesheet" href="login/css/login.css">
    <script src="login/js/login.js"></script>
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
		<div id="j_hi">
			<ul id="j_list">
				<li class=" j_menu" onclick="location.href='/all/cal/<%=m_dto.getId()%>'">캘린더</li>
				<li class=" j_menu" onclick="location.href='/all/community/listArticles.do'">커뮤니티</li>
				<li class=" j_menu">공지사항</li>
				<li class=" j_menu" onclick="location.href='/all/service/allService'" >고객센터</li>
			</ul>
		</div>
	
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

    <section>
		<div id="j_wrap">
			<div id="j_box">
				<!-- 로고 -->
				<div class="e_logo">로그인</div>

				<form name="e_loginform">
					<!-- 아이디, 비밀번호 입력창 -->
					<div class="e_id">
						<h4 id="e_h4_id">아이디</h4>
						<input type="text" name="e_id" id="e_input_id"
							placeholder="아이디를 입력해 주세요." onfocus="this.placeholder=''"
							onblur="this.placeholder='아이디를 입력해 주세요.'">
					</div>

					<div class="e_password">
						<h4 id="e_h4_pass">비밀번호</h4>
						<input type="password" name="e_pw" id="e_input_pass"
							placeholder="비밀번호를 입력해 주세요." onfocus="this.placeholder=''"
							onblur="this.placeholder='비밀번호를 입력해 주세요.'">
					</div>

					<!-- 자동로그인 -->
					<div class="e_auto_login">
						<input type="hidden" name="e_auto_login_check" id="e_auto_login_check" value="N">
						<input type="checkbox" name="e_auto_login"> <span
							id="e_auto_login">자동 로그인</span>
					</div>

					<!-- 로그인 버튼-->
					<div class="e_login">
						<input type="submit" id="e_login_btn" value="로그인">
					</div>

					<!-- 회원가입, 비밀번호 찾기 -->
					<div class="e_other">
						<a class="e_join_member" href="/all/join">회원가입</a> <a
							class="e_find_pass" href="#">비밀번호 찾기</a>
					</div>
				</form>
			</div>
		</div>
	</section>

</body>

</html>