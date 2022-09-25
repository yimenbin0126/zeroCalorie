package eunbin.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eunbin.loginjoin.MemberDAO_e;
import eunbin.loginjoin.MemberDTO_e;
import eunbin.service.DAO.ServiceDAO;
import eunbin.service.DTO.ServiceDTO;

@WebServlet("/service/question-fix")
public class QuestionFixController extends HttpServlet  {

	// 페이지 뷰
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("QuestionFixServlet - get question");
		
		// 세션 생성
		HttpSession session = request.getSession();
		if(session.getAttribute("user")==null) {
			// 로그인 안했을 때 - 잘못된 접근
			// 뒤로가기
			PrintWriter out = response.getWriter();
			out.println("<script language ='javascript'>window.history.back();</script>");
			out.flush();
		} else {
			// 로그인 되있을 때 - 정상 접근
			// 데이터 불러오기 위한 선언
			ServiceDTO s_dto = new ServiceDTO();
			
			// 불러온 값 저장하기
			s_dto = (ServiceDTO)request.getAttribute("s_dto");
			
			// 게시물 번호로 페이지 수정 뷰 가져오기
			request.setAttribute("s_dto", s_dto);
			RequestDispatcher dispatch = request.getRequestDispatcher("/service/jsp/question-fix.jsp");
			dispatch.forward(request, response);
		}
	}
	
	// 게시물 수정
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("QuestionFixServlet - post question");
		
		// 데이터 불러오기 위한 선언
		ServiceDTO s_dto = new ServiceDTO();
		ServiceDAO s_dao = new ServiceDAO();
		
		// 게시물 정보 값 불러오기
		int e_bno = Integer.valueOf(request.getParameter("e_bno"));
		String e_ti_detail_input = request.getParameter("e_ti_detail_input");
		String e_cont_detail_input = request.getParameter("e_cont_detail_input");
		s_dto = s_dao.board_one(e_bno);
		
		// 게시물 수정
		s_dao.board_fix(s_dto.getBno(), e_ti_detail_input, e_cont_detail_input);
		
		// 게시판 메인 페이지로 이동
		PrintWriter out = response.getWriter();
		out.println("<script>location.href='/all/service/question-member'</script>");
		out.flush();
	}
}