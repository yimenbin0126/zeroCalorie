package yoo.calender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CheerMsgDAO {
	
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	private Connection con;
	
	public CheerMsgDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");

			dataFactory = (DataSource) envContext.lookup("jdbc/oracle0303");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public List<CheerMsgVO> read(CalPageMbVO calPageMbVO) {
		List<CheerMsgVO> list = new ArrayList<CheerMsgVO>();

		try {
			con = dataFactory.getConnection();
			
			String query ="";
			query += " WITH T_CHEERMSG_recu(DEPTH, CHR_NO, CHR_MSG, CHR_TIME, ";
			query += " 		FR_MEMBER_NO, TO_MEMBER_NO, CHR_PARENTS_NO) AS ( ";
			query += " SELECT ";
			query += " 	1 AS DEPTH, CHR_NO, CHR_MSG, CHR_TIME, ";
			query += " 					FR_MEMBER_NO, TO_MEMBER_NO, CHR_PARENTS_NO ";
			query += " FROM T_CHEERMSG ";
			query += " WHERE CHR_PARENTS_NO = 0 ";
			query += " UNION ALL ";
			query += " SELECT ";
			query += " 	rc.DEPTH + 1 AS DEPTH, c.CHR_NO, c.CHR_MSG, c.CHR_TIME, ";
			query += " 					c.FR_MEMBER_NO, c.TO_MEMBER_NO, c.CHR_PARENTS_NO ";
			query += " FROM T_CHEERMSG_recu rc ";
			query += " LEFT OUTER JOIN T_CHEERMSG c ON c.CHR_PARENTS_NO = rc.CHR_NO  ";
			query += " WHERE c.CHR_PARENTS_NO > 0  ";
			query += " ) ";
			query += " SEARCH DEPTH FIRST BY CHR_NO DESC SET sort_CHR_NO ";
			query += " SELECT * FROM T_CHEERMSG_recu c LEFT OUTER JOIN T_MEMBER m ON (c.FR_MEMBER_NO = m.MEMBER_NO) ";
			query += " LEFT OUTER JOIN T_MEMBER m ON (c.FR_MEMBER_NO = m.MEMBER_NO) ";
			query += " WHERE c.TO_MEMBER_NO = ? ";
			query += " ORDER BY sort_CHR_NO ";
			
			
	
			
			
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, calPageMbVO.getMEMBER_NO());
//			System.out.println(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				// 각 값 list에 저장
				CheerMsgVO vo = new CheerMsgVO();
				vo.setCHR_NO(rs.getInt("CHR_NO"));
				vo.setCHR_MSG(rs.getString("CHR_MSG"));
				vo.setCHR_TIME(rs.getDate("CHR_TIME"));
				vo.setFR_MEMBER_NO(rs.getInt("FR_MEMBER_NO"));
				vo.setCHR_PARENTS_NO(rs.getInt("CHR_PARENTS_NO"));
				vo.setNICKNAME(rs.getString("NICKNAME"));
				vo.setID(rs.getString("ID"));
				vo.setDEPTH(rs.getInt("DEPTH"));

				list.add(vo);
			}
			rs.close();
			pstmt.close();
			con.close();

		} catch (Exception e) {e.printStackTrace();}
		return list;
	}
	
	
	public void add(CheerMsgVO vo) {
		try {
			con = dataFactory.getConnection();
			String query = " insert into T_CHEERMSG ";
			query += " values(T_CHEERMSG_SEQUENCE.NEXTVAL, ?, sysdate,?, ? , ?) ";

//			System.out.println("preparedStatement :" + query);
			pstmt = con.prepareStatement(query);

			// insert 문에 ? 순서대로 세팅함
			pstmt.setString(1, vo.getCHR_MSG());
			pstmt.setInt(2, vo.getFR_MEMBER_NO());
			pstmt.setInt(3, vo.getTO_MEMBER_NO());
			pstmt.setInt(4, vo.getCHR_PARENTS_NO());

			pstmt.executeUpdate(); // 회원정보를 테이블에 추가
			pstmt.close();
			con.close();

		} catch (Exception e) {e.printStackTrace();}
	}
	
	
	public void empty(CheerMsgVO vo) {
		try {
			
			con = dataFactory.getConnection();
			String query = " UPDATE T_CHEERMSG SET CHR_MSG = NULL ";
			query += " WHERE CHR_NO=? ";
			pstmt = con.prepareStatement(query);
//			System.out.println("preparedStatement : " + query);
			System.out.println("vo.getCHR_NO() : "+ vo.getCHR_NO());
			// 1번째 ? 인 where id= "id"인 애 지움
			pstmt.setInt(1, vo.getCHR_NO());

			// 쿼리문 실행
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	
	

	public void del(CheerMsgVO vo) {
		try {
			con = dataFactory.getConnection();
			String query = "delete from T_CHEERMSG";
			query += " where CHR_NO=?";
			pstmt = con.prepareStatement(query);
//			System.out.println("preparedStatement : " + query);

			// 1번째 ? 인 where id= "id"인 애 지움
			pstmt.setInt(1, vo.getCHR_NO());

			// 쿼리문 실행
			pstmt.executeUpdate();
			pstmt.close();
			con.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	

}