package yoo.calender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import donghyeon.memberDAO;
import donghyeon.memberVO;
import eunbin.loginjoin.MemberDTO_e;

@WebServlet("/cal/*")
public class CalController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);

	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		boolean mypage;					// 내 페이지 인지 확인하는 변수(세션id=페이지id)
		int calPageMbNo;				// 페이지 멤버no
		String calPageMbNickName;		// 페이지 멤버닉넴
		String pageId;					// 페이지 id

		Map pageDateInfo = new HashMap(); // 페이지 날짜정보 map
		MemberDTO_e sessionUserDTO = new MemberDTO_e();	// 접속자 정보
		
		
		//////////세션에 id만 올릴줄 알고 만들었던_일단살려둠
		// 세션 아이디 가져옴 
		// String sessionId ;				// 세션에서 받아올 id
		// sessionId = sessionUser.getId();
		// 세션 id를 이용해서 회원정보 테이블에서 회원정보no 가져옴
		// CalPageMbVO calSessionMbVO = idToMbNo(sessionId);
		//////////////////////////////////////////////////////////////
		
		// 세션에서 접속자의 member_no()를 가져옴
		sessionUserDTO = (MemberDTO_e)request.getSession().getAttribute("user");
		 
		// 세션 정보가 없으면 (로긴 안했으면)
		System.out.println(sessionUserDTO);
		if(sessionUserDTO==null) {
			sendLoginPage(response);
			return;
		}
		
		
		// 주소에서 id 가져옴
		pageId = findId(request);
		
		// 페이지 id를 이용해서 회원정보 테이블에서 회원정보no, 닉넴 가져옴
		CalPageMbVO calPageMbVO = idToMbNo(pageId);
		
		
		// 접속자가 본인 페이지 보는거면 true
		mypage = CheckMyPage( pageId , sessionUserDTO );

		// JSP(뷰)에서 가져온 pageYear, pageMonth 있는지확인 후 있으면 해당날짜 돌려주고, 없으면 오늘날짜 세팅
		pageDateInfo = setPageDate(request);
		
		
		// command 값을 받아옴 (읽기, 추가 삭제 등이 들어오면 수행 (read, add, del))
		reciveCommand(request, calPageMbVO, sessionUserDTO );
		
		
		//////////////// 창 새로고침 되면서 기본적으로 조회되는 것들	
		// >>>>응원msg db에서 조회 
		List<CheerMsgVO> cheerMsglist = cheerMsgRead (calPageMbVO);
		
		// >>>>달력 db에서 조회 
		List<TodoListVO> calTodolist = calTodoRead(request,  pageDateInfo, calPageMbVO);

		// >>>>todolist 조회 
		List<TodoListVO> todoListlist = TodoListRead(request, calPageMbVO, pageDateInfo);
		
		
		//동현씨 부분// 
		// 멤버 객체 생성
		memberDAO dao = new memberDAO();
		memberVO vo = new memberVO();
		List<memberVO> serchMemberlist = new ArrayList<memberVO>();;
			if(request.getParameter("serchID")!=null) {
				String serchID = request.getParameter("serchID");
				vo = dao.searchmember(serchID);
				serchMemberlist.add(vo);
			}else {
				
				serchMemberlist = dao.searchmembers();
			}
			System.out.println("serchMemberlist : "+serchMemberlist.size());
			
		////////////////
		

		// JSP 페이지로 값들 전달
		transCalView(request, response, mypage, calPageMbVO, cheerMsglist, 
								todoListlist, pageDateInfo, calTodolist, serchMemberlist );
	}
	
	public void sendLoginPage(HttpServletResponse response) {
		
		
		
		try {
			response.sendRedirect("/all/calender/sendLoginPage.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 주소에서 id 가져옴
	public String findId(HttpServletRequest request) {
		String urL = request.getServletPath();	// /cal
		String url = request.getRequestURI();	// /yoo_all/cal/sdfgaksjd
		
		// 문자열 자르기 참고 : https://codechacha.com/ko/java-substring-or-split/
		// /yoo_all/cal/sdfgaksjd 을 자른다. (/yoo_all/cal/sdfgaksjd 에서  /cal 여기 글자 위치 + /cal 글자 길이,(부터) /yoo_all/cal/sdfgaksjd  길이 까지 ) =   /sdfgaksjd (슬래쉬 떔에 +1)
		String id = url.substring(url.indexOf(urL)+urL.length()+1,url.length());
		
//		System.out.println(urL);
//		System.out.println(url);
//		System.out.println(url.indexOf(urL));
//		System.out.println(url.length());
		System.out.println("주소에서 id 가져온 id: "+id);

		return id;
	}
	
	// id를 이용해서 회원정보 테이블에서 회원정보no, 닉네임 가져옴
	public CalPageMbVO idToMbNo(String id) {
		
		CalPageMbDAO calPageMbDAO = new CalPageMbDAO();
		CalPageMbVO calPageMbVO = calPageMbDAO.read(id);
//		System.out.println("MEMBER_NO : "+calPageMbVO.getMEMBER_NO());
//		System.out.println("NICKNAME : "+calPageMbVO.getNICKNAME());
//		System.out.println("id로 회원정보 가져오기 성공 ");
		return calPageMbVO;
	}
	
	
	// 접속자가 (sessionID = calPageMbNo) 본인 페이지 보는거면 true
	// (세션에서 로긴된 id와 같으면 응원msg 삭제/ todolist 입력,삭제 버튼 뜸 )
	public boolean CheckMyPage(String pageId, MemberDTO_e sessionUserDTO) {
		return pageId.equals(sessionUserDTO.getId());
	}

	// command 값을 받아옴 (읽기, 추가 삭제 등이 들어오면 수행 (read, add, del))
	public void reciveCommand(HttpServletRequest request, CalPageMbVO calPageMbVO, MemberDTO_e sessionUserDTO) {
		String command = request.getParameter("command");

		// 응원메세지 추가
		if (command != null && command.equals("cheerMsgAdd")) {
			cheerMsgAdd(request, calPageMbVO, sessionUserDTO);
		// 응원메세지 삭제
		} else if (command != null && command.equals("cheerMsgDel")) {
			cheerMsgdel(request);
		// todolist 추가
		} else if (command != null && "tdl_contentsAdd".equals(command)) {
			todoListAdd(request, calPageMbVO);
		// todolist 삭제
		} else if (command != null && "tdl_contentsDel".equals(command)) {
			todoListDel(request);
		// todolist 수정	
		} else if (command != null && "tdl_contentsMod".equals(command)) {
			todoListMod(request);
		}
	}
	
	// >>>>> 응원msg db에서 조회
	public List<CheerMsgVO> cheerMsgRead(CalPageMbVO calPageMbVO) {
		CheerMsgDAO cheerMsgDAO = new CheerMsgDAO();
		List<CheerMsgVO> cheerMsglist = cheerMsgDAO.read(calPageMbVO);
		for (int i = 0; i < cheerMsglist.size(); i++) {
			CheerMsgVO vo = cheerMsglist.get(i);

		/*	System.out.println(" vo.CHR_NO : "+ vo.getCHR_NO() + 
								", vo.CHR_MSG : "+ vo.getCHR_MSG() +
								", vo.CHR_TIME : "+ vo.getCHR_TIME() +
								", vo.NICKNAME : "+ vo.getNICKNAME() +
								", vo.ID : "+ vo.getID() ); */
		}
//		System.out.println("CheerMsg 읽기 성공");
		return cheerMsglist;		
	}
	
	// 응원 msg db에 추가
	public void cheerMsgAdd(HttpServletRequest request, CalPageMbVO calPageMbVO, MemberDTO_e sessionUserDTO ) {
		System.out.println("cheerMsgAdd 요청");
		CheerMsgVO vo = new CheerMsgVO();
		vo.setCHR_MSG(request.getParameter("CHR_MSG"));
		vo.setFR_MEMBER_NO(sessionUserDTO.getMember_no());
        vo.setTO_MEMBER_NO(calPageMbVO.getMEMBER_NO());
        System.out.println(request.getParameter("CHR_MSG"));
        CheerMsgDAO cheerMsgDAO = new CheerMsgDAO();
        cheerMsgDAO.add(vo);
        System.out.println("cheerMsgAdd 성공");
	}
	
	// 응원 msg db에서 삭제
	public void cheerMsgdel(HttpServletRequest request) {
		System.out.println("cheerMsgdel 요청");
		CheerMsgVO vo = new CheerMsgVO();
		vo.setCHR_NO(Integer.parseInt(request.getParameter("CHR_NO")));
		CheerMsgDAO cheerMsgDAO = new CheerMsgDAO();
        cheerMsgDAO.del(vo);
        System.out.println("cheerMsgdel 성공");
	}
	
	// calTodoRead 달력에 월간 정보 조회
	public List<TodoListVO> calTodoRead(HttpServletRequest request, Map pageDateInfo, CalPageMbVO calPageMbVO) {
		List<TodoListVO> calTodolist = List.of();

		String dateMonth = "" + pageDateInfo.get("pageYear") + "-"
				+ (Integer.parseInt(pageDateInfo.get("pageMonth").toString()) + 1);
		TodoListVO todoListVO = new TodoListVO();
		todoListVO.setTdl_date(dateMonth); // 날짜세팅
		todoListVO.setMember_no(calPageMbVO.getMEMBER_NO()); // 페이지회원의id

		TodoListDAO todoListDAO = new TodoListDAO();
		calTodolist = todoListDAO.readMonth(todoListVO);
		for (int i = 0; i < calTodolist.size(); i++) {
			TodoListVO vo = calTodolist.get(i);

		/*	System.out.println(" vo.tdl_no : "+ vo.getTdl_no() + 
								", vo.tdl_date : "+ vo.getTdl_date() +
								", vo.tdl_contents : "+ vo.getTdl_contents() +
								", vo.tdl_category : "+ vo.getTdl_category() +
								", vo.member_no : "+ vo.getMember_no() ); */
	}
	// System.out.println("TodoListlist 읽기 성공");
	return calTodolist;
}

	// >>>>> todolist 조회
	public List<TodoListVO> TodoListRead(HttpServletRequest request, CalPageMbVO calPageMbVO, Map pageDateInfo) {

		List<TodoListVO> TodoListlist = List.of();
		String tdl_date = request.getParameter("tdl_date");
		if (tdl_date == null) {
			tdl_date = "" + pageDateInfo.get("pageYear") + "-"
					+ (Integer.parseInt(pageDateInfo.get("pageMonth").toString()) + 1) + "-" + pageDateInfo.get("pageDate");
		}
		
		// 클릭한 달력 날짜가 있으면
		TodoListVO todoListVO = new TodoListVO();
		todoListVO.setTdl_date(tdl_date); // 날짜
		todoListVO.setMember_no(calPageMbVO.getMEMBER_NO()); // 페이지회원의id
	
		TodoListDAO todoListDAO = new TodoListDAO();
		TodoListlist = todoListDAO.read(todoListVO);

		for (int i = 0; i < TodoListlist.size(); i++) {
			TodoListVO vo = TodoListlist.get(i);
	
			/* System.out.println(" vo.tdl_no : " + vo.getTdl_no() + ", vo.tdl_date : " + vo.getTdl_date()
					+ ", vo.tdl_contents : " + vo.getTdl_contents() + ", vo.tdl_category : " + vo.getTdl_category()
					+ ", vo.member_no : " + vo.getMember_no());*/
		}
		//System.out.println("TodoListlist 읽기 성공");
		return TodoListlist;
	}
		
	
	// todoList 추가
	public void todoListAdd(HttpServletRequest request, CalPageMbVO calPageMbVO) {
		TodoListDAO TodoListDAO1 = new TodoListDAO();
		TodoListVO vo = new TodoListVO();
		
		int pageYear = Integer.parseInt(request.getParameter("pageYear"));
		int pageMonth =  Integer.parseInt(request.getParameter("pageMonth"))+1;
		int pageDate = Integer.parseInt(request.getParameter("pageDate"));

		String tdl_date = pageYear+"-"+pageMonth+"-"+pageDate;
		
		vo.setTdl_contents( request.getParameter("tdl_contents") );
		vo.setTdl_category(request.getParameter("tdl_category") );
		vo.setTdl_date(tdl_date);
		vo.setMember_no(calPageMbVO.getMEMBER_NO());
		TodoListDAO1.add(vo);
	}
	
	// todoList 삭제
	public void todoListDel(HttpServletRequest request) {
		TodoListDAO TodoListDAO1 = new TodoListDAO();
		TodoListVO vo = new TodoListVO();
		vo.setTdl_no( Integer.parseInt(request.getParameter("tdl_no") ));
		TodoListDAO1.del(vo);
	}

	// todoList 수정
	public void todoListMod(HttpServletRequest request) {
		TodoListDAO TodoListDAO1 = new TodoListDAO();
		TodoListVO vo = new TodoListVO();
		vo.setTdl_no( Integer.parseInt(request.getParameter("tdl_no") ));
		vo.setTdl_category( (request.getParameter("tdl_category") ));
		vo.setTdl_contents( (request.getParameter("tdl_contents") ));
		TodoListDAO1.mod(vo);
	}
	
	// JSP(뷰)에서 가져온 pageYear, pageMonth 있는지확인 후 있으면 해당날짜 돌려주고, 없으면 오늘날짜 세팅
	public Map setPageDate(HttpServletRequest request) {
		Map<String, Integer> pageDateInfo = new HashMap<String, Integer>();

		// JSP(뷰)에서 가져온 날짜가 있으면 그대로 돌려줌 ( 클릭>새로고침 되도 페이지 이어짐을 위해)
		if ((request.getParameter("pageYear") != null)) {
			pageDateInfo.put("pageYear", Integer.parseInt(request.getParameter("pageYear")));
			pageDateInfo.put("pageMonth", Integer.parseInt(request.getParameter("pageMonth")));
			pageDateInfo.put("pageDate", Integer.parseInt(request.getParameter("pageDate")));

			System.out.println("JSP에서 가져온 날짜 : "+pageDateInfo.get("pageYear")+", "+pageDateInfo.get("pageMonth")+", "+pageDateInfo.get("pageDate"));
		
		// 가져온 날짜 없으면 오늘 날짜 세팅
		} else {
			GregorianCalendar now = new GregorianCalendar();
			pageDateInfo.put("pageYear", now.get(1));
			pageDateInfo.put("pageMonth", now.get(2));
			pageDateInfo.put("pageDate", now.get(5));

			System.out.println("Servlet 에서 오늘 날짜 세팅 :"+pageDateInfo.get("pageYear")+", "
														+pageDateInfo.get("pageMonth")+", "+pageDateInfo.get("pageDate"));
		}
		
		return pageDateInfo;
	}
	
	// JSP 페이지로 값들 전달
 	public void transCalView(HttpServletRequest request, HttpServletResponse response, 
 								boolean mypage, CalPageMbVO calPageMbVO, List<CheerMsgVO> cheerMsglist, 
 								List<TodoListVO> todoListlist, Map pageDateInfo, List<TodoListVO> calTodolist,
 								List<memberVO> serchMemberlist) {
 		
		request.setAttribute("mypage", mypage);
		request.setAttribute("calPageMbVO", calPageMbVO);
		request.setAttribute("cheerMsglist", cheerMsglist);
		request.setAttribute("todoListlist", todoListlist);
		request.setAttribute("pageDateInfo", pageDateInfo);
		request.setAttribute("calTodolist", calTodolist);
		
		request.setAttribute("serchMemberlist", serchMemberlist);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/calender/cal.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

}

