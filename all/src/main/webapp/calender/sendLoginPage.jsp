<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.msg_div{
		display: block;
		margin: 0px auto ;
		margin-top: 130px ;
		border: 1px dotted gray;
		width: 600px;
		text-align: center;
	}
</style>
<script>
	/* 5초 뒤에 페이지 이동 */
	let yoo_sendTimer = setTimeout(() => {
		location.href='/all/login';
	}, 5000);

	/* 타이머 해제 */
	/* clearTimeout(yoo_sendTimer); */
	
	let i=5;
	
	/*1초마다 갱신*/
	setInterval(() => {
		--i;
		document.querySelector("h3").innerHTML = i+"초" ;
		
	}, 1000);

</script>
</head>
<body>
	<div class="msg_div">
		<h1>로그인 후에 이용해주세요.</h1> 
		<h1>로그인 창으로 5초 뒤에 이동합니다.</h1>
		<h3 >5초</h3>	
	</div>	
</body>
</html>