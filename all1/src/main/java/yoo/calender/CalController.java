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
		
		System.out.println("-----------------------------------------------");
		
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
		//TODO : 응원메세지 조회 대신 페이징
//		List<CheerMsgVO> cheerMsglist = cheerMsgRead (calPageMbVO);
		
		// 페이징
		Map chrPagingMap = paging(request,  response, calPageMbVO);
		
		// >>>>달력 db에서 조회 
		List<TodoListVO> calTodolist = calTodoRead(request,  pageDateInfo, calPageMbVO);

		// >>>>todolist 조회 
		List<TodoListVO> todoListlist = TodoListRead(request, calPageMbVO, pageDateInfo);
		
		// >>>> 회원 조회  
		List<memberVO> serchMemberlist = searchUser(request);
		
		// JSP 페이지로 값들 전달
//		transCalView(request, response, mypage, calPageMbVO, cheerMsglist, 
//								todoListlist, pageDateInfo, calTodolist, serchMemberlist );
		transCalView(request, response, mypage, calPageMbVO, chrPagingMap,
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
		String urI = request.getRequestURI();	// /all/cal/qwer

//		String id = urI.substring(urI.indexOf(urL)+urL.length()+1,urI.length());

		int starIdx = urI.lastIndexOf("/");
		int endIdx = urI.length();
		
		// if ?가 있으면 ?로 자른다
		if(urI.lastIndexOf("?") != -1) {
			endIdx = urI.lastIndexOf("?");
		}
		String id = urI.substring(starIdx+1, endIdx);
		
		
		System.out.println("urL : "+urL);
		System.out.println("urI : "+urI);
//		System.out.println(urI.indexOf(urL));
//		System.out.println(urI.length());

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
			
			
			
			// 페이징 db 한바퀴 돌아서 전부가져옴
			ChrPagingDAO dao = new ChrPagingDAO();
			List<CheerMsgVO> cheerMsglist = dao.selectPagingList( calPageMbVO , 0, dao.selectListCount(calPageMbVO));

			CheerMsgVO cheerMsgVO = new CheerMsgVO();

			ArrayList<Integer> pCHR_NOList = new ArrayList<Integer>() ;
			
			int nowDepth = 1;
			int nextDepth = 1;	// 다음 댓글의 자식 유무 판단
			for(int i =0; i<cheerMsglist.size(); i++) {
				cheerMsgVO = (CheerMsgVO)cheerMsglist.get(i);
				
				// 받아온 chr_no의 i를 찾음
				if(cheerMsgVO.getCHR_NO()==Integer.parseInt(request.getParameter("CHR_NO"))){
					
					// 현재 depth를 저장
					nowDepth = cheerMsgVO.getDEPTH();
					
					// 다음 댓글의 번호가 1이면 자식(대댓글)이 없고, 2 이면 있는것
					if (i< cheerMsglist.size()-2) {
						
						CheerMsgVO nextVO = new CheerMsgVO();
                		nextVO = (CheerMsgVO)cheerMsglist.get(i+1);
                		nextDepth = nextVO.getDEPTH();
                		
                	// 다음 댓글이 없으면 자식이 없으므로 지워져도 되니까 다음댓글을 1로 줌
					}else {
						nextDepth = 1;
					}
					
					// 지금 댓글의 원글 갯수와 정보를 보냄(parentsDapth)
					// 레벨 은 1부터 시작, 현재 내 레벨 빼기 때문에 -1
					// for 내 dapth-1 만큼 돌림 	
					Loop1:
					for( int pDapth = 1; pDapth<cheerMsgVO.getDEPTH(); pDapth++){
						
						// if 이전 댓글이 null 인지? 맞으면 (같이 지울 삭제된 댓글 있는지?)
						if(cheerMsglist.get(i-pDapth).getCHR_MSG() == null){
							
							// if depth가 내 depth -1인가  맞으면 (근데 그게 내 부모댓글 인지?)
							if(cheerMsglist.get(i-pDapth).getDEPTH() == cheerMsgVO.getDEPTH()-pDapth){
								// chr_no 저장
								pCHR_NOList.add(cheerMsglist.get(i-pDapth).getCHR_NO());
								
							}else{// else  : 돌필요 없음
								// for문 끝냄
								break Loop1;
							}	
						}
						else{ // else : 돌필요 없음
							// for문 끝냄
							break;
						}
					 }
				}
			} 
			
			
			// 대댓글이 없는 글이라면 삭제 (다음 댓글의 Depth가 현재 Depth 보다 작거나 같으면)
			// 하고 위에 부모댓글도 내용이 없으면 삭제
			if(nextDepth<=nowDepth) {
				System.out.println("nextDepth : "+nextDepth);
				cheerMsgDel(request, pCHR_NOList); 
				
			// 대댓글이 있다면 내용만 비움(삭제된 메세지입니다로 표시하게됨)
			}else {
				System.out.println("nextDepth : "+nextDepth);
				cheerMsgEmpty(request);
			}

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
		try {
			System.out.println("cheerMsgAdd 요청");
			CheerMsgVO vo = new CheerMsgVO();
			vo.setCHR_MSG(request.getParameter("CHR_MSG"));
			vo.setFR_MEMBER_NO(sessionUserDTO.getMember_no());
	        vo.setTO_MEMBER_NO(calPageMbVO.getMEMBER_NO());
	        vo.setCHR_PARENTS_NO(Integer.parseInt(request.getParameter("CHR_PARENTS_NO")));
	        System.out.println(request.getParameter("CHR_MSG"));
	        CheerMsgDAO cheerMsgDAO = new CheerMsgDAO();
	        cheerMsgDAO.add(vo);
	        System.out.println("cheerMsgAdd 성공");
		}catch(Exception e) {e.printStackTrace();}
	}
	
	// 응원 msg db에서 메세지만 비움
	public void cheerMsgEmpty(HttpServletRequest request) {
		try {
			System.out.println("cheerMsgEmpty 요청");
			CheerMsgVO vo = new CheerMsgVO();
			vo.setCHR_NO(Integer.parseInt(request.getParameter("CHR_NO")));
			CheerMsgDAO cheerMsgDAO = new CheerMsgDAO();
	        cheerMsgDAO.empty(vo);
	        System.out.println("cheerMsgEmpty 성공");
		}catch(Exception e) {e.printStackTrace();}
	}
	// 응원 msg db에서 삭제
	public void cheerMsgDel(HttpServletRequest request, List<Integer> pCHR_NOList) {
		try {
			System.out.println("cheerMsgdel 요청");
			CheerMsgVO vo = new CheerMsgVO();
			vo.setCHR_NO(Integer.parseInt(request.getParameter("CHR_NO")));
			CheerMsgDAO cheerMsgDAO = new CheerMsgDAO();
	        cheerMsgDAO.del(vo);
	        System.out.println("cheerMsgdel 성공");
		
        
        
	        /////// 부모댓글도 지울 기능 넣을지 말지 고민되서 따로 넣음
	        if(pCHR_NOList !=null) {
	        	
	    		for(int CHR_NO : pCHR_NOList) {
	    			System.out.println("삭제할 부모 CHR_NO : "+CHR_NO);
	    			vo.setCHR_NO(CHR_NO);
	    			
	    			cheerMsgDAO.del(vo);
	    		}
	        }
	        ///////////////////////////////////////////////////////////
		}catch(Exception e) {e.printStackTrace();}
	}
	
	
	// calTodoRead 달력에 월간 정보 조회
	public List<TodoListVO> calTodoRead(HttpServletRequest request, Map pageDateInfo, CalPageMbVO calPageMbVO) {
		List<TodoListVO> calTodolist = new ArrayList<TodoListVO>();

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
		try {
			TodoListDAO TodoListDAO1 = new TodoListDAO();
			TodoListVO vo = new TodoListVO();
			
			int pageYear = Integer.parseInt(request.getParameter("pageYear"));
			int pageMonth =  Integer.parseInt(request.getParameter("pageMonth"))+1;
			int pageDate = Integer.parseInt(request.getParameter("pageDate"));
	
			String tdl_date = pageYear+"-"+pageMonth+"-"+pageDate;
			
			vo.setTdl_contents( request.getParameter("tdl_contents") );
			//vo.setTdl_contents( "ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ" );
			vo.setTdl_category(request.getParameter("tdl_category") );
			vo.setTdl_date(tdl_date);
			vo.setMember_no(calPageMbVO.getMEMBER_NO());
			TodoListDAO1.add(vo);
		}catch(Exception e) {e.printStackTrace();}
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
	
	// 친구 검색
	public List<memberVO> searchUser(HttpServletRequest request) {

		memberDAO dao = new memberDAO();
		memberVO vo = new memberVO();
		List<memberVO> serchMemberlist = new ArrayList<memberVO>();
		
			// 입력된 id 값이 있을시 검색
			if(request.getParameter("serchID")!=null && request.getParameter("serchID").trim().length() != 0 ) {
				String serchID = request.getParameter("serchID");
				vo = dao.searchmember(serchID);
				serchMemberlist.add(vo);
				
				return serchMemberlist;
			// 입력된 id 값이 없을시 검색
			}else {
				return serchMemberlist = dao.searchmembers();
			}
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
 								boolean mypage, CalPageMbVO calPageMbVO, Map chrPagingMap, 
 								List<TodoListVO> todoListlist, Map pageDateInfo, List<TodoListVO> calTodolist,
 								List<memberVO> serchMemberlist) {
 		
		request.setAttribute("mypage", mypage);
		request.setAttribute("calPageMbVO", calPageMbVO);
		request.setAttribute("chrPagingMap", chrPagingMap);
		request.setAttribute("todoListlist", todoListlist);
		request.setAttribute("pageDateInfo", pageDateInfo);
		request.setAttribute("calTodolist", calTodolist);
		
		request.setAttribute("serchMemberlist", serchMemberlist);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/calender/cal.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
 	
 	//>>> 페이징 관련
 	
 	public Map paging(HttpServletRequest request, HttpServletResponse response, CalPageMbVO calPageMbVO) {
		
		
		int[] countPerPageArr = {2,3,4,5}; // 한 페이지당 보여줄 글 개수
		
		// 기본값
		int pageNum = 1; // 현재페이지
		int countPerPage = 3;
		
		String str_pageNum = request.getParameter("pageNum");
		String str_countPerPage = request.getParameter("countPerPage");
		
		
		
//		if (str_pageNum != null) {
//			pageNum = Integer.parseInt(str_pageNum);
//		}
//		if (str_countPerPage != null) {
//			countPerPage = Integer.parseInt(str_countPerPage);
//		}
		
		try {
			pageNum = Integer.parseInt(str_pageNum);
		} catch (NumberFormatException nfe) {}
		
		try {
			// 변환한 str_countPerPage 의 값이 배열 길이 안에 있으면 저장해줌
			if(Integer.parseInt(str_countPerPage) < countPerPageArr.length) {
				countPerPage = Integer.parseInt(str_countPerPage);
			}
		} catch (NumberFormatException nfe) {}

		
		Map chrPagingMap = getPagingList(calPageMbVO, pageNum, countPerPage, countPerPageArr);
		chrPagingMap.put("pageNum", pageNum);
		chrPagingMap.put("countPerPage", countPerPage);
		chrPagingMap.put("countPerPageArr", countPerPageArr);
		chrPagingMap.put("uri", request.getRequestURI());

		return chrPagingMap;
	}
	
	public Map getPagingList(CalPageMbVO calPageMbVO, int pageNum, int countPage, int[] countPerPageArr){
		
		countPage = countPerPageArr[countPage];
		
		System.out.println("countPage : "+countPage);
		ChrPagingDAO dao = new ChrPagingDAO();
		
		int start=0, end=0;
		
		// 1,5
		// start = 1, end = 5
		// 2,5
		// start = 6, end = 10
		// 3,5
		// start = 11, end = 15
		
		start = ((pageNum-1)*countPage) +1;
		end = pageNum*countPage;
		end = start + countPage-1;
		
		
		List<CheerMsgVO> list = dao.selectPagingList(calPageMbVO, start, end);
		
		int count =  dao.selectListCount(calPageMbVO);
		
		Map chrPagingMap = new HashMap();
		chrPagingMap.put("list", list);
		chrPagingMap.put("count", count);
		
		System.out.println("count : " + count);
		
		return chrPagingMap;
				
	}

}

