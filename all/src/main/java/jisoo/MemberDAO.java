package jisoo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class MemberDAO {
	
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	
	//db연결
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle0303");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public MemberVO listMembers(int member_no) {
		MemberVO vo = new MemberVO();
		try {
			con = dataFactory.getConnection();
			
			String query = "select * from t_member";
			
			pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("id");
				String pw = rs.getString("pw");
				String name = rs.getString("name");
				String nickname = rs.getString("nickname");
				int height = rs.getInt("height");
				String gender = rs.getString("gender");
				Date birth = rs.getDate("birth");
				String tel = rs.getString("tel");
				String email = rs.getString("email");
				
				vo.setId(id);
				vo.setPw(pw);
				vo.setName(name);
				vo.setNickname(nickname);
				vo.setGender(gender);
				vo.setBirth(birth);
				vo.setTel(tel);
				vo.setEmail(email);
				vo.setHeigth(height);
				
			}
			rs.close();
			pstmt.close();
			con.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}
	
	public void editMember(MemberVO vo) {
		String id = vo.getId();
		String gender = vo.getGender();
		String name = vo.getName();
		String nickname = vo.getNickname();
		String email = vo.getEmail();
		String tel = vo.getTel();
		int height = vo.getHeight();
		System.out.println(id);
		System.out.println(gender);
		System.out.println(name);
		System.out.println(nickname);
		System.out.println(email);
		System.out.println(tel);
		System.out.println(height);
		try {
			con = dataFactory.getConnection();
			String query = " update member set name=?, nickname=?, gender=?, tel=?, email=?, height=? where id=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, nickname);
			pstmt.setString(3, gender);
			pstmt.setString(4, tel);
			pstmt.setString(5, email);
			pstmt.setInt(6, height);
			pstmt.setString(7, id);
			
			pstmt.executeUpdate();
			System.out.println(pstmt.executeUpdate());
					
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
