<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    import="eunbin.loginjoin.MemberDAO_e,eunbin.loginjoin.MemberDTO_e,eunbin.service.DAO.ServiceDAO,eunbin.service.DTO.ServiceDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" href="join/css/header.css">
    <link rel="stylesheet" href="join/css/join.css">
    <script src="join/js/join.js"></script>
    <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.3.0/jquery.form.min.js" integrity="sha384-qlmct0AOBiA2VPZkMY3+2WqkHtIQ9lSdAsAn5RUJD/3vA5MKDgSGcdmIv4ycVxyn" crossorigin="anonymous"></script>
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

	<section>
		<!-- 프로필 파일 올릴때  -->
		<form name="e_mainform" enctype="multipart/form-data">
			<div id="j_wrap">
				<div id="j_box">

					<!-- 뒤로가기(로그인하기) -->
					<div class="e_back">
						<input type="button" id="e_back_btn"
							onClick="location.href='/all/main'" value="&lt;메인으로 이동">
					</div>

					<!-- 로고 -->
					<div class="e_logo">회원가입</div>

					<!-- 아이디 -->
					<div class="e_id">
						<h4 id="e_h4_id">아이디</h4>
						<div id="e_id_confirm" class="e_errorindex">*영문 소문자와 숫자를 혼합해
							5~9자리로 만들어주세요.</div>
						<input type="text" name="e_id" id="e_input_id"
							placeholder="아이디를 입력해 주세요." onfocus="this.placeholder=''"
							onblur="this.placeholder='아이디를 입력해 주세요.'">

						<!-- 아이디 중복체크 -->
						<div class="e_idcheck_div">
							<button type="button" id="e_idCheck">중복 확인</button>
							<!-- 중복체크 완료시 -->
							<div id="e_id_check" class="e_checkindex">*사용 가능한 아이디 입니다.
							</div>
						</div>
					</div>


					<!-- 비밀번호 -->
					<div class="e_password">
						<h4 id="e_h4_pass">비밀번호</h4>
						<div id="e_pass_confirm" class="e_errorindex">*특수문자(!@#*),
							영문 대문자, 영문 소문자, 숫자 최소 한개 이상을 넣어 혼합해 5~9자리로 만들어주세요.</div>
						<input type="password" name="e_pw" id="e_input_pass"
							placeholder="비밀번호를 입력해 주세요." onfocus="this.placeholder=''"
							onblur="this.placeholder='비밀번호를 입력해 주세요.'">
					</div>

					<!-- 비밀번호 확인 -->
					<div class="e_password_more">
						<h4 id="e_h4_pass_more">비밀번호 확인</h4>
						<div id="e_pass_more_confirm" class="e_errorindex">*비밀번호가 맞지
							않습니다.</div>
						<input type="password" id="e_input_pass_more"
							placeholder="비밀번호를 다시 입력해 주세요." onfocus="this.placeholder=''"
							onblur="this.placeholder='비밀번호를 다시 입력해 주세요.'">
					</div>

					<!-- 이름 -->
					<div class="e_name">
						<h4 id="e_h4_name">이름</h4>
						<div id="e_name_confirm" class="e_errorindex">*이름을 입력해주세요.</div>
						<input type="text" name="e_name" id="e_input_name"
							placeholder="이름을 입력해 주세요." onfocus="this.placeholder=''"
							onblur="this.placeholder='이름을 입력해 주세요.'">
					</div>

					<!-- 닉네임 -->
					<div class="e_nickname">
						<h4 id="e_h4_nickname">닉네임</h4>
						<div id="e_nickname_confirm" class="e_errorindex">*2자~5자 이하의
							한글로 만들어주세요.</div>
						<input type="text" name="e_nickname" id="e_input_nickname"
							placeholder="닉네임을 입력해 주세요." onfocus="this.placeholder=''"
							onblur="this.placeholder='닉네임을 입력해 주세요.'">
						
						<!-- 닉네임 중복체크 -->
						<div class="e_nickcheck_div">
							<button type="button" id="e_nickCheck">중복 확인</button>
							<!-- 중복체크 완료시 -->
							<div id="e_nick_check" class="e_checkindex">*사용 가능한 닉네임 입니다.
							</div>
						</div>
					</div>

					<!-- 생년월일 -->
					<div class="e_birth">
						<h4 id="e_h4_birth">생년월일</h4>
						<div id="e_birth_confirm" class="e_errorindex">*생년월일을
							입력해주세요.</div>
						<input type="text" name="e_birth" id="e_input_birth"
							placeholder="ex) 2000-06-29" onfocus="this.placeholder=''"
							onblur="this.placeholder='ex) 2000-06-29'">
					</div>

					<!-- 성별 -->
					<div class="e_gender">
						<h4 id="e_h4_gender">성별</h4>
						<div id="e_gender_confirm" class="e_errorindex">*성별을 선택해주세요.</div>
						<select name="e_gender" id="e_sel_gender">
							<option value="e_none">선택</option>
							<option value="F">여자</option>
							<option value="M">남자</option>
						</select>
					</div>

					<!-- 이메일 -->
					<div class="e_email">
						<h4>이메일</h4>
						<div id="e_email_confirm" class="e_errorindex">*이메일 형식이 맞지
							않습니다.</div>
						<input type="email" name="e_email" id="e_input_email"
							placeholder="ex) abc1234@gmail.com" onfocus="this.placeholder=''"
							onblur="this.placeholder='ex) abc1234@gmail.com'">
					</div>

					<!-- 전화번호 -->
					<div class="e_tel">
						<h4>전화번호</h4>
						<div id="e_tel_confirm" class="e_errorindex">*전화번호 형식이 맞지
							않습니다.</div>
						<input type="text" name="e_tel" id="e_input_tel"
							placeholder="ex) 010-1234-5678" onfocus="this.placeholder=''"
							onblur="this.placeholder='ex) 010-1234-5678'">
					</div>

					<!-- 키 -->
					<div class="e_height">
						<h4 id="e_h4_height">본인의 키</h4>
						<div id="e_height_confirm" class="e_errorindex">*본인의 키를 제대로
							입력해주세요.</div>
						<input type="text" name="e_height" id="e_input_height"
							placeholder="본인의 키를 입력해 주세요." onfocus="this.placeholder=''"
							onblur="this.placeholder='본인의 키를 입력해 주세요.'"> <span>cm</span>
					</div>

					<!-- 임시 (지금은 사용안함) -->
					<!-- 프로필 사진 -->
					<div class="e_pro_img">
						<h4 id="e_h4_pro_img">프로필 사진</h4>
						<div id="e_pro_img_confirm" class="e_errorindex">*jpg/jpeg/png 파일을 첨부해주세요.</div>
						<!-- 파일 업로드 -->
						<label class="e_input_pro_img_btn" for="e_input_pro_img">
							프로필 사진 업로드 </label>
						<input type="file" name="e_pro_img" id="e_input_pro_img" accept=".jpg, .jpeg, .png">
						<!-- 파일 유효성 검사, 삭제하기 -->
						<button type="button" id="e_delete_file">삭제</button>
						<!-- 이미지 미리보기, 파일이름 -->
						<div class="e_pro_img_view">
							<img>
						</div>
						<div class="e_pro_img_name">
						</div>
					</div>

					<!-- 회원가입 버튼 -->
					<div class="e_sub">
						<input type="hidden" name="e_sub_btn_YN" id="e_sub_btn_YN" value="Y">
						<input type="submit" id="e_sub_btn" value="회원가입">
					</div>
				</div>
			</div>
		</form>
	</section>

</body>

</html>