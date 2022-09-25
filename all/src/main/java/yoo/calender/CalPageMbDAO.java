package yoo.calender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CalPageMbDAO {
	
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	private Connection con;
	
	public CalPageMbDAO() {
		try {

			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");

			dataFactory = (DataSource) envContext.lookup("jdbc/oracle0303");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CalPageMbVO read(String id){
		CalPageMbVO vo = new CalPageMbVO();
		try {
			con = dataFactory.getConnection();
			String query = " SELECT MEMBER_NO, NICKNAME,PRO_IMG  FROM T_MEMBER WHERE ID = LOWER(?) ";

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id );
//			System.out.println(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				vo.setMEMBER_NO(rs.getInt("MEMBER_NO"));
				vo.setNICKNAME(rs.getString("NICKNAME"));
				vo.setPRO_IMG(rs.getString("PRO_IMG"));
			}
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {e.printStackTrace();}
		return vo;
	}

}
