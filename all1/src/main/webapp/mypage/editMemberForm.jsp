<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="eunbin.loginjoin.MemberDAO_e" %>
<%@page import="eunbin.loginjoin.MemberDTO_e" %>
<%@page import="java.util.*" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <link rel="stylesheet" href="editMemberForm.css">
</head>
<script>
	function fn_sendMember() {
		var frmMember = document.frmMember;
		var id = frmMember.id.value;
		var name = frmMember.name.value;
		var nickname = frmMember.nickname.value;
		var gender = frmMember.gender.value;
		var height = frmMember.height.value;
		var tel = frmMember.tel.value;
		var birth = frmMember.birth.value;
		var email = frmMember.email.value;
		frmMember.submit();
	}
	
</script>
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
			<div id="j_sec1">
				<h1><%=m_dto.getNickname() %>님의 프로필 수정
				</h1>
				<form name="frmMember" action="/all/mypage/editMemberForm" method="post">
					<div class="k_profile_detail">
						<div class="k_t_box">
							<input type="hidden"
								value="<%= m_dto.getId() %>" name="id">
							<div id="name">
								이름 : <input type="text"
									value="<%=m_dto.getName() %>" name="name">
							</div>
							<div id="nickname">
								닉네임 : <input type="text"
									value="<%=m_dto.getNickname() %>"
									name="nickname">
							</div>
						</div>
						<div class="k_t_box">
							<div id="gender">
								성별 : <input type="text"
									value="<%=m_dto.getGender() %>"
									name="gender">
							</div>
							<div id="height">
								키 : <input type="text"
									value="<%=m_dto.getHeight() %>"
									name="height">
							</div>
						</div>
						<div class="k_t_box2">
							<div id="tel">
								전화번호 : <input type="text"
									value="<%=m_dto.getTel() %>" name="tel">
							</div>
						</div>
						<div class="k_t_box2">
							<div id="email">
								이메일 : <input type="text"
									value="<%=m_dto.getEmail() %>"
									name="email">
							</div>
						</div>
						<div>
							<input id="k_save_btn" type="submit" value="저장하기"
								onclick="fn_sendMember();"> <input type="hidden"
								value="editMember" name="command">
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>


</body>
</html>