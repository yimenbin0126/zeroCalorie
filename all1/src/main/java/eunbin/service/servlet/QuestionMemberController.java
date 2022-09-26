package eunbin.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/service/question-member")
public class QuestionMemberController extends HttpServlet  {

	// 페이지 뷰
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("QuestionServlet - get question_member");
		
		// 데이터 불러오기 위한 선언
		ServiceDTO s_dto = new ServiceDTO();
		ServiceDAO s_dao = new ServiceDAO();
		List<ServiceDTO> s_dto_list = new ArrayList<ServiceDTO>();
		
		// 첫 화면 - 회원정보 관리
		// 관리자 회원번호로 게시물들 불러오기
		s_dto_list = s_dao.board_All("question_member");
		if(s_dto_list!=null && s_dto_list.size() !=0) {
			request.setAttribute("s_dto_list", s_dto_list);
		} else {
			System.out.println("QuestionServlet - question_member - 존재하는 게시물 없음");
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("/service/jsp/question-member.jsp");
		dispatch.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

}