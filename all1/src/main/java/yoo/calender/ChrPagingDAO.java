package yoo.calender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ChrPagingDAO {
	
	// 해당 페이지의 목록만 조회
	public List<CheerMsgVO> selectPagingList(CalPageMbVO calPageMbVO, int start, int end){ 
		List<CheerMsgVO> result = new ArrayList<CheerMsgVO>();

		try {
			// DB 접속
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			DataSource dataFactory = (DataSource) envContext.lookup("jdbc/oracle0303");
			Connection con=dataFactory.getConnection();
			
			// SQL 준비
			String query = "" ;
			query += " SELECT * FROM( ";
			query += " 		SELECT rownum AS rnum, msg.*  FROM ( ";
			query += " 			WITH T_CHEERMSG_recu(DEPTH, CHR_NO, CHR_MSG, CHR_TIME, ";
			query += " 						FR_MEMBER_NO, TO_MEMBER_NO, CHR_PARENTS_NO) AS (  ";
			query += " 				SELECT ";
			query += " 					1 AS DEPTH, CHR_NO, CHR_MSG, CHR_TIME, ";
			query += " 									FR_MEMBER_NO, TO_MEMBER_NO, CHR_PARENTS_NO ";
			query += " 				FROM T_CHEERMSG ";
			query += " 				WHERE CHR_PARENTS_NO = 0 ";
			query += " 				UNION ALL ";
			query += " 				SELECT ";
			query += " 					rc.DEPTH + 1 AS DEPTH, c.CHR_NO, ";
			query += " 								c.CHR_MSG, c.CHR_TIME, ";
			query += " 									c.FR_MEMBER_NO, c.TO_MEMBER_NO, c.CHR_PARENTS_NO ";
			query += " 				FROM T_CHEERMSG_recu rc ";
			query += " 				LEFT OUTER JOIN T_CHEERMSG c ON c.CHR_PARENTS_NO = rc.CHR_NO  ";
			query += " 				WHERE c.CHR_PARENTS_NO > 0  ";
			query += " 				) ";
			query += " 				SEARCH DEPTH FIRST BY CHR_NO DESC SET sort_CHR_NO ";
			query += " 				SELECT * FROM T_CHEERMSG_recu c LEFT OUTER JOIN T_MEMBER m ON (c.FR_MEMBER_NO = m.MEMBER_NO) "; 
			query += " 				LEFT OUTER JOIN T_MEMBER m ON (c.FR_MEMBER_NO = m.MEMBER_NO) ";
			query += " 				WHERE c.TO_MEMBER_NO = ? ";
			query += " 				ORDER BY sort_CHR_NO  ";
			query += " 					) msg ";
			query += " 		) ";
			query += "	WHERE ?<= rnum AND rnum<=? ";

			//실행 준비
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setInt(1, calPageMbVO.getMEMBER_NO());
			ps.setInt(2, start);
			ps.setInt(3, end);
			
			//실행 
			ResultSet rs = ps.executeQuery();
			
			
			while( rs.next() ) {
				CheerMsgVO vo = new CheerMsgVO();
				vo.setCHR_NO(rs.getInt("CHR_NO"));
				vo.setCHR_MSG(rs.getString("CHR_MSG"));
				vo.setCHR_TIME(rs.getDate("CHR_TIME"));
				vo.setFR_MEMBER_NO(rs.getInt("FR_MEMBER_NO"));
				vo.setCHR_PARENTS_NO(rs.getInt("CHR_PARENTS_NO"));
				vo.setNICKNAME(rs.getString("NICKNAME"));
				vo.setID(rs.getString("ID"));
				vo.setDEPTH(rs.getInt("DEPTH"));
				vo.setRNUM(rs.getInt("RNUM"));
				
				result.add(vo);
			}
			con.close();
		} catch (Exception e) {e.printStackTrace();}		
		
		System.out.println(start);
		System.out.println(end);
		
		return result;
	}
	
	
	// 전체 글 개수 가져오기
	public int selectListCount(CalPageMbVO calPageMbVO){ 
		int result = -1; // 안탄거 확인하려고 초기값 -1

		try {
			// DB 접속
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			DataSource dataFactory = (DataSource) envContext.lookup("jdbc/oracle0303");
			Connection con=dataFactory.getConnection();
			
			// SQL 준비
			String query = "" ;
			query += "SELECT count(*) cnt FROM T_CHEERMSG where TO_MEMBER_NO = ?";


			//실행 준비
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1, calPageMbVO.getMEMBER_NO());

			
			//실행 
			ResultSet rs = ps.executeQuery();
			
			
			//결과 처리 
			if(rs.next()) { // 첫번째 행의 첫번째 값
				result = rs.getInt("cnt");

			}
			con.close();
		} catch (Exception e) {e.printStackTrace();}		

		return result;
	}

}
