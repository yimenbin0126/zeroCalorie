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

// 회원가입
@WebServlet("/joinComplet")
public class JoinCheckController extends HttpServlet {

	// 페이지 뷰
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("JoinServlet - get join");
	}

	// 회원가입
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("JoinServlet - post join");
		System.out.println("회원가입 실행");
		System.out.println("request getContentType : " + request.getContentType());

		// 파일 경로
		String savePath = "C:\\zerokalory_file";
		// 파일 크기 15MB
		int sizeLimit = 1024 * 1024 * 15;
		// 파라미터를 전달해줌 (같은 이름의 파일명 방지)
		MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "utf-8", new DefaultFileRenamePolicy());
		
		// 회원정보 불러오기
		String id = multi.getParameter("e_id");
		String pw = multi.getParameter("e_pw");
		String name = multi.getParameter("e_name");
		String nickname = multi.getParameter("e_nickname");
		String gender = multi.getParameter("e_gender");
		Date birth = Date.valueOf((String) multi.getParameter("e_birth"));
		String tel = multi.getParameter("e_tel");
		String email = multi.getParameter("e_email");
		int height = Integer.valueOf((String) multi.getParameter("e_height"));
		// 프로필 이미지 파일
		// getFilesystemName() : 파일 이름 받아오기
		String fileName = multi.getFilesystemName("e_pro_img");
		// 파일의 전체 경로
		String pro_img = savePath + "/" + fileName;

		// SQL 문을 수행할 dao, dto 추가
		MemberDAO_e dao = new MemberDAO_e();
		MemberDTO_e dto = new MemberDTO_e();
		
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
		
		// 로그인 홈페이지로 이동
		PrintWriter out = response.getWriter();
		out.println("<script>location.href='/all/login';</script>");
	}

}