package eunbin.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet("/service/question-detail")
public class QuestionDetailController extends HttpServlet  {

	// 페이지 뷰
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("QuestionDetailServlet - get question");
		
		// 로그인 되있을 때 - 정상 접근
		// 데이터 불러오기 위한 선언
		MemberDTO_e m_dto = new MemberDTO_e();
		MemberDAO_e m_dao = new MemberDAO_e();
		ServiceDTO s_dto = new ServiceDTO();
		ServiceDAO s_dao = new ServiceDAO();
		
		// 게시물 대표번호로 페이지 뷰 가져오기
		int e_bno_val = Integer.valueOf(request.getParameter("e_bno_val"));
		s_dto = s_dao.board_one(e_bno_val);
		
		request.setAttribute("s_dto", s_dto);
		RequestDispatcher dispatch = request.getRequestDispatcher("/service/jsp/question-detail.jsp");
		dispatch.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("QuestionDetailServlet - post question");
		
		// 데이터 불러오기 위한 선언
		ServiceDTO s_dto = new ServiceDTO();
		ServiceDAO s_dao = new ServiceDAO();
		
		// 값 불러오기
		String btn = request.getParameter("e_btn");
		int e_bno = Integer.valueOf(request.getParameter("e_bno"));
		// 게시판 불러오기
		s_dto = s_dao.board_one(e_bno);
		
		if(btn.equals("fix")) {
			// 게시물 수정 버튼 누를시
			request.setAttribute("s_dto", s_dto);
			
			// 수정 페이지로 이동
			RequestDispatcher dispatch = request.getRequestDispatcher("/service/jsp/question-fix.jsp");
			dispatch.forward(request, response);
			
		} else if (btn.equals("delete")) {
			// 게시물 삭제 버튼 누를시
			s_dao.board_delete(s_dto.getBno());
			
			// 게시판 메인 화면으로 이동
			PrintWriter out = response.getWriter();
			out.println("<script>location.href='/all/service/question-member'</script>");
			out.flush();
		}
	}
}