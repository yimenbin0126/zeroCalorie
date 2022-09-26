<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false"
    import="eunbin.loginjoin.MemberDTO_e, tackjun.member.MemberDAO,
    tackjun.community.board.ArticleVO" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
<head>
   <meta charset="UTF-8">
   <title>글보기</title>
   <link rel="stylesheet" href="/all/board_community/css/viewArticle.css">
   <link rel="stylesheet" href="/all/board_community/css/header.css">
   <style>
     #tr_btn_modify{
       display:none;
     }
   
   </style>
   <script src="http://code.jquery.com/jquery-latest.min.js"></script> 
   <script type="text/javascript" >
     function backToList(obj){
	    obj.action="${contextPath}/community/listArticles.do";
	    obj.submit();
     }
 
	 function fn_enable(obj){
		 document.getElementById("i_title").disabled=false;
		 document.getElementById("i_content").disabled=false;
		 document.getElementById("tr_btn_modify").style.display="block";
		 document.getElementById("tr_btn").style.display="none";
	 }
	 
	 function fn_modify_article(obj){
		 obj.action="${contextPath}/community/modArticle.do";
		 obj.submit();
	 }
	 
	 function fn_remove_article(url,articleNO){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var articleNOInput = document.createElement("input");
	     articleNOInput.setAttribute("type","hidden");
	     articleNOInput.setAttribute("name","articleNO");
	     articleNOInput.setAttribute("value", articleNO);
		 
	     form.appendChild(articleNOInput);
	     document.body.appendChild(form);
	     form.submit();
	 
	 }
	 
	 function fn_reply_form(url, parentNO){
		 var form = document.createElement("form");
		 form.setAttribute("method", "post");
		 form.setAttribute("action", url);
	     var parentNOInput = document.createElement("input");
	     parentNOInput.setAttribute("type","hidden");
	     parentNOInput.setAttribute("name","parentNO");
	     parentNOInput.setAttribute("value", parentNO);
		 
	     form.appendChild(parentNOInput);
	     document.body.appendChild(form);
		 form.submit();
	 }
	 
	 function readURL(input) {
	     if (input.files && input.files[0]) {
	         var reader = new FileReader();
	         reader.onload = function (e) {
	             $('#preview').attr('src', e.target.result);
	         }
	         reader.readAsDataURL(input.files[0]);
	     }
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

			  <form name="frmArticle" method="post" action="${contextPath}" enctype="multipart/form-data">
			  <h1 class="first_h1">글 작성</h1>
			  <table border=0 align="center">
			   <%
			   		ArticleVO a_vo = new ArticleVO();
			   		a_vo = (ArticleVO)request.getAttribute("article");
					MemberDTO_e dto = new MemberDTO_e();
					
				  	int member_no = a_vo.getMember_no();
				  	dto = m_dao.load_member(member_no);
				  	String nickname = dto.getNickname();
				%>
			  
			  <tr>
			    <td width="150" align="center" bgcolor="#FF9933" class="e_td">
			      제목 
			   </td>
			   <td>
			    <input type=text value="${article.title }"  name="title"  id="i_title" disabled class="e_input" />
			   </td>   
			  </tr>
			  <tr>
			    <td width="150" align="center" bgcolor="#FF9933" class="e_td">
			      내용
			   </td>
			   <td>
			    <textarea rows="20" cols="60" name="content" id="i_content" disabled />${article.content }</textarea>
			   </td>  
			  </tr>
			 
			<c:if test="${not empty article.imageFileName && article.imageFileName!='null' }">  
			<tr>
			    <td width="150" align="center" bgcolor="#FF9933" rowspan="2">
			      이미지
			   </td>
			   <td>
			     <input type="hidden" name="originalFileName" value="${article.imageFileName }" />
			    <img src="${contextPath}/download_t.do?articleNO=${article.articleNO}&imageFileName=${article.imageFileName}" id="preview"  /><br>
			       
			   </td>   
			  </tr>  
			  <tr>
			    <td>
			       <input type="file" name="imageFileName" id="i_imageFileName" disabled   onchange="readURL(this);"   />
			    </td>
			  </tr>
			 </c:if>
			  <tr>
				   <td width="150" align="center" bgcolor="#FF9933" class="e_td">
				      등록일자
				   </td>
				   <td>
				    <input type=text value="<fmt:formatDate value="${article.writeDate}" />" disabled class="e_input" />
				   </td>   
			  </tr>
			  <tr id="tr_btn_modify">
				   <td colspan="3"   align="center" >
				        <input type=button id="btn_update" value="수정반영하기" onClick="fn_modify_article(frmArticle)"  >
			            <input type=button id="btn_no" value="취소" onClick="backToList(frmArticle)">
				   		<input type="hidden" name="articleNO" value="${article.articleNO}">
				   </td>   
			  </tr>
			  	<tr class="blank"></tr>
			    
			  <tr id="tr_btn">
			   <td colspan="2" align="center">
			    <%
					if((MemberDTO_e)session.getAttribute("user") !=null ){
						MemberDTO_e new_m_dto = (MemberDTO_e)session.getAttribute("user");
						if(new_m_dto.getMember_no()==member_no){
				%>
				    <input type=button value="수정하기" onClick="fn_enable(this.form)">
				    <input type=button value="삭제하기" onClick="fn_remove_article('${contextPath}/community/removeArticle.do', ${article.articleNO})">
				     <input type=button value="답글쓰기" style="display:none;" onClick="fn_reply_form('${contextPath}/community/replyForm.do', ${article.articleNO})">
			  <%
					}
					}
			  %>
				    <input type=button value="리스트로 돌아가기" onClick="backToList(this.form)" class="back_btn">
			   </td>
			  </tr>
			 </table>
			 </form>
			 
			 </div>
 
			</div>
		</div>
	</section>
 
</body>
</html>