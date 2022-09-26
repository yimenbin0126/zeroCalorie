package yoo.calender;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownload
 */
@WebServlet("/download.do")
public class FileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 경로 확보
		String file_repo = "C:\\zerokalory_file";
		// 파일명 확보
		String fileName = (String) request.getParameter("fileName");
		//파일에 대한 풀 경로(file full path) (윈도우라서 \\ 해줌)
		String downFile = file_repo +"\\"+ fileName;
		// 이러면 리눅스나 윈도우 상관없이 쓸수 있음
		// String downFile = file_repo +System.getProperty("file.separator")+ fileName;
		// String downFile = file_repo + File.separator+ fileName;
//		System.out.println("폴더 구분자 1: "+System.getProperty("file.separator"));
//		System.out.println("폴더 구분자 2: "+ File.separator);
		
		// 파일 그 자체
		File f = new File(downFile);
		
		// 파일을 읽어오기 위한 세팅
		FileInputStream in = new FileInputStream( f );
		// 브라우져가 cache를 사용하는방법
		// no-cache : cache를 사용하지 않는 방법
		response.setHeader("Cache-Control", "no-cache");
		// 지금 브라우져가 받은 내용이 파일이라는 것을 명시
		// 그 파일을 다운로드할대 초기값을 명시
		response.addHeader("Content-disposition", "attachment; fileName="+"file.txt");

		OutputStream out = response.getOutputStream();
		
		
		byte[] buf = new byte[1024 * 8]; //8kb
		while(true) {
			// 파일 읽기(buffer 크기만큼 읽어서 buffer에 저장)
			int count = in.read(buf);
			// 읽을 내용이 없으면 -1을 반환....
			if(count == -1) {
				break;
			}
			out.write(buf, 0, count);
		}
		
		in.close();
		out.close();

	}

	
	
}
