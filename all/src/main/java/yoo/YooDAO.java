package yoo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class YooDAO {
	
	// 오라클 드라이버 class
	private String driver = "oracle.jdbc.driver.OracleDriver";
	// @ :~에(at)
	private String url = "jdbc:oracle:thin:@todair.synology.me:51521:xe";
	private String user = "scott2_13";
	private String pwd = "tiger";
	
	private Statement stmt;
	private Connection con;

	private void connDB() {
		try {
			// String변수 driver를 이용해 class를 생성한다
			Class.forName(driver);
			System.out.println("Oracle 드라이버 로딩 성공");
			
			// DB 접속
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("Connection 생성 성공");
			
			// SQL 실행 변수 생성
			stmt = con.createStatement();
			System.out.println("Statement 생성 성공");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<YooVO> listYoo(){
		List<YooVO> list = new ArrayList<YooVO>();
		
		try {
			connDB();
			String query = "select * from CURRENT_WEIGHT_WEEK";
			System.out.println(query);
			
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String id = rs.getString("id");
				int current_weight = rs.getInt("current_weight");
				int target_weight = rs.getInt("target_weight");
				Date record_date = rs.getDate("record_date");
				System.out.println(no);
				YooVO vo = new YooVO();
				vo.setNo(no);
				vo.setId(id);
				vo.setCurrent_weight(current_weight);
				vo.setTarget_weight(target_weight);
				vo.setRecord_date(record_date);
				
				list.add(vo);
			}
			rs.close();
			stmt.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}