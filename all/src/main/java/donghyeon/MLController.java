package donghyeon;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import addMember.memberVO;

/**
 * Servlet implementation class memberList
 */
@WebServlet("/list")
public class MLController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 뷰 페이지
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
	
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
		dispatcher.forward(request, response);
	}
	
	// 데이터를 받고 변한 뷰 페이지
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		
		// 멤버 객체 생성
		memberDAO dao = new memberDAO();
		memberVO vo = new memberVO();
		if(request.getParameter("id")!=null) {
			String id = request.getParameter("id");
			vo = dao.searchmember(id);
			request.setAttribute("vo", vo);
		}
		// 멤버들 리스트에 담기
		List<memberVO> list = dao.searchmembers();
		// 개인 멤버 전달
		// 담은 멤버 리스트 전달 (YN = 전달유무)
		if(request.getParameter("YN")!=null) {
			request.setAttribute("list", list);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
		dispatcher.forward(request, response);
	}
	

	


}