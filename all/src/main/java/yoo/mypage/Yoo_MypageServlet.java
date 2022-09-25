package yoo.mypage;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet("/yoo2")
public class Yoo_MypageServlet extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		Yoo_MypageDAO dao = new Yoo_MypageDAO();
		Yoo_MypageVO yooVO = new Yoo_MypageVO();
		int MEMBER_NO = Integer.parseInt(request.getParameter("MEMBER_NO"));
		yooVO.setMEMBER_NO(MEMBER_NO);
		
		List<Yoo_MypageVO> list = dao.listYoo(yooVO);
		
		PrintWriter out = response.getWriter();
		
		String json = JSON_test(list);
		response.getWriter().print(json);
		
		response.setContentType("application/json");
	    response.setHeader("Access-Control-Allow-Origin", "*");
		
	}
	
	public String JSON_test(List<Yoo_MypageVO> list) {
		
		String json="[";
		
		JSONObject JSON_DB = new JSONObject();
		
		for(int i=0; i<list.size(); i++) {
			Yoo_MypageVO vo = list.get(i);
			JSON_DB.put("WEIGHT_NO", vo.getWEIGHT_NO());
			JSON_DB.put("MEMBER_NO", vo.getMEMBER_NO());
			JSON_DB.put("CURRENT_WEIGHT", vo.getCURRENT_WEIGHT());
			JSON_DB.put("TARGET_WEIGHT", vo.getTARGET_WEIGHT());
			JSON_DB.put("WEIGHT_DATE", vo.getWEIGHT_DATE().toString());

			json += JSON_DB.toJSONString(); 
			
			if(i+1<list.size()) {
				json += ",";
			}
		}
		json += "]";
		System.out.println(json);
		
		return json;
	}

}

