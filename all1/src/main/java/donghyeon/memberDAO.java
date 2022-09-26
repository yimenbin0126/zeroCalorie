package donghyeon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

//import addMember.memberVO;

public class memberDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource df;
	
	public memberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			df = (DataSource) envContext.lookup("jdbc/oracle0303");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	// 아이디를 통해서 멤버 정보 불러오기
	public memberVO searchmember(String new_id){
		List<memberVO> list = new ArrayList<memberVO>();
		memberVO vo = new memberVO();
		try {
			con = df.getConnection();
			String query = "select * from t_member where id =";
			query += "'"+new_id+"'";
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String name = rs.getString("name");
				String id = rs.getString("id");
				String nickname = rs.getString("nickname");
				vo.setId(id);
				vo.setName(name);
				vo.setNickname(nickname);
			}
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return vo;
	}
	
	public List<memberVO> searchmembers(){
		List<memberVO> list = new ArrayList<memberVO>();
		try {
			con = df.getConnection();
			String query = "select * from t_member";
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				memberVO vo = new memberVO();
				
				String name = rs.getString("name");
				String id = rs.getString("id");
				String nickname = rs.getString("nickname");
				vo.setId(id);
				vo.setName(name);
				vo.setNickname(nickname);
				System.out.println(vo.toString());
				list.add(vo);
			}
			rs.close();
			pstmt.close();
			con.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return list;
	}
	
	
}
