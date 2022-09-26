<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"
    isELIgnored="false"
    import="eunbin.loginjoin.MemberDTO_e,tackjun.member.MemberDAO" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"  /> 
<head>
<meta charset="UTF-8">
<title>글쓰기창</title>
<link rel="stylesheet" href="/all/board_community/css/articleForm.css">
<link rel="stylesheet" href="/all/board_community/css/header.css">
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
   function readURL(input) {
      if (input.files && input.files[0]) {
	      var reader = new FileReader();
	      reader.onload = function (e) {
	        $('#preview').attr('src', e.target.result);
          }
         reader.readAsDataURL(input.files[0]);
      }
  }  
  function backToList(obj){
    obj.action="${contextPath}/community/listArticles.do";
    obj.submit();
  }
</script>
 <title>새글 쓰기 창</title>
</head>
<body>

<!-- 헤더 시작 -->
    <div id="j_hi">
        <!-- <img src="./img/logo.png" id="j_logo"> -->

		<%
		// 데이터 불러오기 위한 선언
		MemberDTO_e m_dto = new MemberDTO_e();
		MemberDAO m_dao = new MemberDAO();

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
		<div id="j_wrap">
			<div id="j_box">

				<div id="e_box">
					<h1 style="text-align: center">글쓰기</h1>
					<form name="articleForm" method="post"
						action="${contextPath}/community/addArticle.do"
						enctype="multipart/form-data">
						<div class="e_box_write">

							<div class="header_title">
								<div class="title">글제목</div>
								<div>
									<input type="text" name="title" id="title_detail" />
								</div>
							</div>

							<div class="header_content">
								<div class="title">글내용</div>
								<div>
									<textarea name="content" maxlength="4000"
										id="content_detail"></textarea>
								</div>
							</div>
							
							<div>
								<div>
									<div class="image_content">
										<div align="right">
											<div>이미지파일 첨부</div>
											<div>
												<input type="file" name="imageFileName"
												onchange="readURL(this);" />
											</div>
										</div>
										<div class="show">
											<img id="preview" src="#" width=200 height=200
												style="display: none;" />
										</div>
									</div>
								</div>
							</div>

							<div>
								<div class="btn">
									<input type="submit" value="글쓰기" />
									<input type=button
										value="목록보기" onClick="backToList(this.form)" />
								</div>
							</div>
						</div>
					</form>
				</div>

			</div>
		</div>
	</section>
</body>
</html>