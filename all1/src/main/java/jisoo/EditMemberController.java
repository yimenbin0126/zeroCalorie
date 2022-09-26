package jisoo;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eunbin.loginjoin.MemberDAO_e;
import eunbin.loginjoin.MemberDTO_e;

// 은빈 수정
@WebServlet("/mypage/editMemberForm")
public class EditMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		MemberDTO_e m_dto = new MemberDTO_e();
		MemberDAO_e m_dao = new MemberDAO_e();
		
		if(request.getParameter("id")!=null) {
			String id = request.getParameter("id");
			// 아이디로 정보 불러오기
			m_dto = m_dao.load_login(id);
			// 정보값 전달
			request.setAttribute("m_dto", m_dto);
			
			RequestDispatcher dispatch = request.getRequestDispatcher("/mypage/editMemberForm.jsp");
			dispatch.forward(request, response);
		} else {
			RequestDispatcher dispatch = request.getRequestDispatcher("/mypage/mypage.jsp");
			dispatch.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		MemberDTO_e m_dto = new MemberDTO_e();
		MemberDAO_e m_dao = new MemberDAO_e();
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String gender = request.getParameter("gender");
		int height = Integer.parseInt(request.getParameter("height"));
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		
		m_dto.setId(id);
		m_dto.setName(name);
		m_dto.setNickname(nickname);
		m_dto.setGender(gender);
		m_dto.setHeight(height);
		m_dto.setTel(tel);
		m_dto.setEmail(email);
		
		m_dao.updateMember(m_dto);
		
		// 업데이트
		MemberDTO_e m_dto_new = new MemberDTO_e();
		m_dto_new = m_dao.load_login(id);
		HttpSession session = request.getSession();
		session.setAttribute("user", m_dto_new);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("/mypage/mypage.jsp");
		dispatch.forward(request, response);
	}
}
