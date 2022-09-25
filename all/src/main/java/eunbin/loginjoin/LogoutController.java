package eunbin.loginjoin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 로그아웃
@WebServlet("/logout")
public class LogoutController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("LogoutServlet - get logout");
		
		// 로그인 세션 불러와 로그아웃
		HttpSession session = request.getSession();
		session.invalidate();
		// 쿠키 내역 있는지 확인 - 자동 로그인 해제
		Cookie[] cookies = request.getCookies();
		if(cookies !=null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userId")) {
					// 유효기간 0으로 만들어 초기화
					Cookie del_c = new Cookie("userId", "");
					del_c.setMaxAge(0);
					response.addCookie(del_c);
				}
			}
		}
		
		PrintWriter out = response.getWriter();
		out.println("<script language ='javascript'>alert('로그아웃 하였습니다.'); location.href='/all/main'; </script>");
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("LogoutServlet - post logout");
	}
}