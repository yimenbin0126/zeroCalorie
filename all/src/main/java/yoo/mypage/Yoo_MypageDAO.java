package yoo.mypage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import yoo.calender.TodoListVO;

public class Yoo_MypageDAO {

	private PreparedStatement pstmt;
	private DataSource dataFactory;
	private Connection con;

	public Yoo_MypageDAO() {
		try {

			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");

			dataFactory = (DataSource) envContext.lookup("jdbc/oracle0303");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Yoo_MypageVO> listYoo(Yoo_MypageVO yooVO) {
		List<Yoo_MypageVO> list = new ArrayList<Yoo_MypageVO>();

		try {
			con = dataFactory.getConnection();
			String query = "select * from T_WEIGHT_RCD WHERE MEMBER_NO  = ? order by WEIGHT_NO ";
			System.out.println(query);

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, yooVO.getMEMBER_NO());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				Yoo_MypageVO vo = new Yoo_MypageVO();
				vo.setWEIGHT_NO(rs.getInt("WEIGHT_NO"));
				vo.setMEMBER_NO(rs.getInt("MEMBER_NO"));
				vo.setCURRENT_WEIGHT(rs.getInt("CURRENT_WEIGHT"));
				vo.setTARGET_WEIGHT(rs.getInt("TARGET_WEIGHT"));
				vo.setWEIGHT_DATE(rs.getDate("WEIGHT_DATE"));

				list.add(vo);
			}
			rs.close();
			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void add(Yoo_MypageVO vo) {
		try {
			con = dataFactory.getConnection();

			String query = " insert into T_WEIGHT_RCD ";
			query += " values(T_WEIGHT_RCD_SEQUENCE.NEXTVAL, ?, ?, sysdate,? ) ";

			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, vo.getCURRENT_WEIGHT());
			pstmt.setInt(2, vo.getTARGET_WEIGHT());
			pstmt.setInt(3, vo.getMEMBER_NO());

			pstmt.executeUpdate();
			pstmt.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mod(Yoo_MypageVO vo) {

		try {
			con = dataFactory.getConnection();

			String query = " UPDATE T_WEIGHT_RCD ";
			query += " SET CURRENT_WEIGHT=?, TARGET_WEIGHT = ? ";
			query += " WHERE MEMBER_NO =? and to_char(WEIGHT_DATE,'yyyy-mm-dd') = to_char(sysdate,'yyyy-mm-dd') ";

//				UPDATE T_WEIGHT_RCD
//				 SET CURRENT_WEIGHT='30',  TARGET_WEIGHT = '30'
//						 WHERE MEMBER_NO ='1' and WEIGHT_DATE = to_char(sysdate,'yyyy-mm-dd');

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, vo.getCURRENT_WEIGHT());
			pstmt.setInt(2, vo.getTARGET_WEIGHT());
			pstmt.setInt(3, vo.getMEMBER_NO());
			
			 System.out.println(vo.getCURRENT_WEIGHT());
			  System.out.println(vo.getTARGET_WEIGHT());
			  System.out.println(vo.getMEMBER_NO());
			 

			System.out.println("mod 다녀왓다");
			
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}