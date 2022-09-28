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
	h2{
		display: inline-block;
	}

</style>
<script>
	/* 3초 뒤에 페이지 이동 */
	let yoo_sendTimer = setTimeout(() => {
		location.href='/all/login';
	}, 3000);

	/* 타이머 해제 */
	/* clearTimeout(yoo_sendTimer); */
	
	let i=3;
	
	/*1초마다 갱신*/
	setInterval(() => {
		--i;
		document.querySelector("#sec").innerHTML = i+"초" ;
		
	}, 1000);

</script>
</head>
<body>
	<div class="msg_div">
		<h1>로그인 후에 이용해주세요.</h1>
		<br>
		<h2>로그인 창으로</h2> <h2 id="sec">3초</h2> <h2> 뒤에 이동합니다.</h2>
		
	</div>	
</body>
</html>