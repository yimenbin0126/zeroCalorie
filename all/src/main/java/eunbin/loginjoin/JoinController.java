package eunbin.loginjoin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

// 회원가입 체크
@WebServlet("/join")
public class JoinController extends HttpServlet {

	// 페이지 뷰
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("JoinServlet - get join");

		// 세션 생성
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			// 로그인 안했을 때 - 정상 접근
			RequestDispatcher dispatch = request.getRequestDispatcher("/join/jsp/join.jsp");
			dispatch.forward(request, response);
		} else {
			// 로그인 되있을 때 - 잘못된 접근
			// 뒤로가기
			PrintWriter out = response.getWriter();
			out.println("<script>location.href='/all/main';</script>");
			out.flush();
		}
	}

	// 회원가입 - 항목별 체크
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("JoinServlet - post join");

		// SQL 문을 수행할 dao, dto 추가
		MemberDAO_e dao = new MemberDAO_e();
		MemberDTO_e dto = new MemberDTO_e();

		// 중복체크
		if (request.getParameter("e_idcheck_click") != null
				&& request.getParameter("e_idcheck_click").equals("Y")) {
			// 아이디 중복 체크 - 값 다시 전달
			String id = request.getParameter("e_input_id");
			PrintWriter out = response.getWriter();
			out.print(dao.idCheck(id));
		} else if (request.getParameter("e_nickCheck_click") != null
				&& request.getParameter("e_nickCheck_click").equals("Y")) {
			// 닉네임 중복 체크 - 값 다시 전달
			String nickname = request.getParameter("e_input_nick");
			PrintWriter out = response.getWriter();
			out.print(dao.nickCheck(nickname));
		}
	}

	

	// 회원가입
	private void join2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("JoinServlet - post join - join");
		System.out.println("회원가입 실행");
		
		// SQL 문을 수행할 dao, dto 추가
		MemberDAO_e dao = new MemberDAO_e();
		MemberDTO_e dto = new MemberDTO_e();

		// 파일 폴더에 업로드 하기
		// 파일 경로
		File filePath = new File("C:\\zerokalory_file");
		// 업로드된 파일을 저장할 저장소와 관련된 클래스
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 파일 저장 경로 설정 (File 객체를 이용)
		factory.setRepository(filePath);
		// 최대 업로드 가능한 파일 크기를 설정 (byte)
		factory.setSizeThreshold(1024 * 1024);
		// 이미지 파일경로 폴더에 저장 기능
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 파일 고유 이름
		String fileName = "";

		try {
			// FileItem 리스트로 받기
			List<FileItem> items = upload.parseRequest(request);
			// 업로드 된 항목들을 하나씩 가져와
			// FileItem : 전송된 폼 데이터 -> 파일 등의 아이템으로 표현하는 객체
			for (int i = 0; i < items.size(); i++) {
				FileItem fileItem = (FileItem) items.get(i);
				// 폼 필드X 파일만 불러오기. 파일 최소 1개 이상
				if (fileItem.isFormField() == false && fileItem.getSize() > 0) {
					// 맨 마지막의 \ 뒤에 나오는 '파일이름'의 위치
					int idx = fileItem.getName().lastIndexOf("\\");
					if (idx == 1) {
						idx = fileItem.getName().lastIndexOf("/");
					}
					// 파일 이름을 저장
					fileName = fileItem.getName().substring(idx + 1);

					// 업로드한 파일 이름으로 저장소에 파일을 업로드
					File uploadFile = new File(filePath + "\\" + fileName);
					fileItem.write(uploadFile);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 회원정보 불러오기
		String id = request.getParameter("e_id");
		String pw = request.getParameter("e_pw");
		String name = request.getParameter("e_name");
		String nickname = request.getParameter("e_nickname");
		String gender = request.getParameter("e_gender");
		Date birth = Date.valueOf((String) request.getParameter("e_birth"));
		String tel = request.getParameter("e_tel");
		String email = request.getParameter("e_email");
		int height = Integer.valueOf((String) request.getParameter("e_height"));
		// 프로필 이미지 생략
		// String pro_img = request.getParameter("e_pro_img");
		String pro_img = fileName;

		// 회원정보 정의
		dto.setId(id);
		dto.setPw(pw);
		dto.setName(name);
		dto.setNickname(nickname);
		dto.setGender(gender);
		dto.setBirth(birth);
		dto.setTel(tel);
		dto.setEmail(email);
		dto.setHeight(height);
		dto.setPro_img(pro_img);

		// 회원정보 추가
		dao.addMember(dto);
		System.out.println("회원가입 성공");

		RequestDispatcher dispatch = request.getRequestDispatcher("/login/jsp/login.jsp");
		dispatch.forward(request, response);
	}

}