<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    import="eunbin.loginjoin.MemberDTO_e,tackjun.member.MemberDAO,tackjun.community.board.ArticleVO,java.util.*"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
<%
request.setCharacterEncoding("UTF-8");
%>  
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/all/board_community/css/listArticle.css">
<link rel="stylesheet" href="/all/board_community/css/header.css">
 <style>
   .cls1 {text-decoration:none;}
   .h{text-align:center;}
  </style>
  
  <meta charset="UTF-8">
  <title>게시판 글 목록</title>
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
					<h1 class="h">커뮤니티 게시판</h1>
					<table align="center"  width="80%"  >
					  <tr align="center"  id="table_header">
					     <td >글번호</td>
					     <td >작성자</td>              
					     <td >제목</td>
					     <td >작성일</td>
					  </tr>
					<c:choose>
					  <c:when test="${empty articlesList }" >
					    <tr  height="10">
					      <td colspan="4">
					         <p align="center">
					            <b><span style="font-size:9pt; color: red;">등록된 글이 없습니다.</span></b>
					        </p>
					      </td>  
					    </tr>
					  </c:when>
							<c:when test="${!empty  articlesList}">
								
								
									<tr align="center">
									<%
									List<ArticleVO> articlesList = new ArrayList<ArticleVO>();
									articlesList = (List<ArticleVO>) request.getAttribute("articlesList");
									for (int i = 0; i < articlesList.size(); i++) {
										MemberDTO_e dto = new MemberDTO_e();
										ArticleVO a_vo = new ArticleVO();
										a_vo = articlesList.get(i);

										int member_no = a_vo.getMember_no();
										System.out.println(member_no);
										dto = m_dao.load_member(member_no);
										System.out.println(dto.getNickname());
										String nickname = dto.getNickname();
									%>
									<td width="5%"><%=i+1%></td>
										<td width="10%">
										<%=nickname%>
										</td>
										<td align='left' width="35%"><span
											style="padding-right: 30px"></span> <c:choose>
												<c:when test='${article.articleNO > 1 }'>
													<c:forEach begin="1" end="<%=a_vo.getArticleNO()%>" step="1">
														<span style="padding-left: 10px"></span>
													</c:forEach>
													<span style="font-size: 12px;">┗[답변]</span>
													<a class='cls1'
														href="${contextPath}/community/viewArticle.do?articleNO=<%=a_vo.getArticleNO()%>"><%=a_vo.getTitle()%></a>
												</c:when>
												<c:otherwise>
													<a class='cls1'
														href="${contextPath}/community/viewArticle.do?articleNO=<%=a_vo.getArticleNO()%>"><%=a_vo.getTitle()%></a>
												</c:otherwise>
											</c:choose></td>
										<td width="10%"><fmt:formatDate
												value="<%=a_vo.getWriteDate()%>" /></td>
									</tr>
								<%
								}
								%>
								
								
							</c:when>
						</c:choose>
					</table>
					<%
					if((MemberDTO_e)session.getAttribute("user") !=null){
					%>
					<div class="btn_class">
						<a  class="cls1"  href="${contextPath}/community/articleForm.do">글쓰기</a>
					</div>	
					<%
						}
					%>
				</div>
			</div>
		</div>
	</section>
	
</body>
</html>