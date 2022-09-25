package jisoo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eunbin.loginjoin.MemberDTO_e;
import yoo.mypage.Yoo_MypageDAO;
import yoo.mypage.Yoo_MypageVO;

@WebServlet("/mypage")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MemberDAO dao = new MemberDAO();
		String command = request.getParameter("command");
		
		if(request.getParameter("command")!=null && "editMember".equals(command)) {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String nickname = request.getParameter("nickname");
			String gender = request.getParameter("gender");
			int height = Integer.parseInt(request.getParameter("height"));
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			
			
					
			MemberVO vo = new MemberVO();
			vo.setId(id);
			vo.setName(name);
			vo.setNickname(nickname);
			vo.setGender(gender);
			vo.setHeigth(height);
			vo.setTel(tel);
			vo.setEmail(email);
			
			dao.editMember(vo);
			System.out.println("editmember 실행");
			
			
			// 소연 부분
		}else if ((command != null && command.equals("weightAdd"))) {
			
			MemberDTO_e sessionUserDTO = new MemberDTO_e();	// 접속자 정보
			sessionUserDTO = (MemberDTO_e)request.getSession().getAttribute("user");
			
			Yoo_MypageDAO yooDAO = new Yoo_MypageDAO();
			Yoo_MypageVO yooVO = new Yoo_MypageVO();
			yooVO.setCURRENT_WEIGHT(Integer.parseInt(request.getParameter("CURRENT_WEIGHT")));
			yooVO.setTARGET_WEIGHT(Integer.parseInt(request.getParameter("TARGET_WEIGHT")));
			yooVO.setMEMBER_NO(sessionUserDTO.getMember_no());
			yooDAO.add(yooVO);
		}else if ((command != null && command.equals("weightMod"))) {
			 
			MemberDTO_e sessionUserDTO = new MemberDTO_e();	// 접속자 정보
			sessionUserDTO = (MemberDTO_e)request.getSession().getAttribute("user");
			System.out.println("mod 다녀왓다");
			Yoo_MypageDAO yooDAO = new Yoo_MypageDAO();
			Yoo_MypageVO yooVO = new Yoo_MypageVO();
			yooVO.setCURRENT_WEIGHT(Integer.parseInt(request.getParameter("CURRENT_WEIGHT")));
			yooVO.setTARGET_WEIGHT(Integer.parseInt(request.getParameter("TARGET_WEIGHT")));
			yooVO.setMEMBER_NO(sessionUserDTO.getMember_no());
			
	
			yooDAO.mod(yooVO);
		}
		
		response.sendRedirect("/all/mypage/mypage.jsp");
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
