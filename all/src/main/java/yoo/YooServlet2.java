package yoo;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperBuilder;

@WebServlet("/yoo2")
public class YooServlet2 extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		YooDAO dao = new YooDAO();
		List<YooVO> list = dao.listYoo();
		
		PrintWriter out = response.getWriter();
		
		String json = JSON_test(list);
		response.getWriter().print(json);
		
		response.setContentType("application/json");
	    response.setHeader("Access-Control-Allow-Origin", "*");
		
	}
	
	public String JSON_test(List<YooVO> list) {
		
		String json="[";
		
		JSONObject JSON_DB = new JSONObject();
		
		for(int i=0; i<list.size(); i++) {
			YooVO vo = list.get(i);
			JSON_DB.put("no", vo.getNo());
			JSON_DB.put("id", vo.getId());
			JSON_DB.put("current_weight", vo.getCurrent_weight());
			JSON_DB.put("target_weight", vo.getTarget_weight());
			JSON_DB.put("record_date", vo.getRecord_date().toString());

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

