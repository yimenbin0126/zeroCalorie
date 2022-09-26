package yoo.calender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class TodoListDAO {
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	private Connection con;


	public TodoListDAO() {
		try {
	
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
	
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle0303");
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 월간 todolist 읽기 (달력표시용)
	public List<TodoListVO> readMonth (TodoListVO todoListVO ){
		List<TodoListVO> list = new ArrayList<TodoListVO>();
		
		try {
			con = dataFactory.getConnection();
			String query = " SELECT * FROM T_TODOLIST WHERE MEMBER_NO = ? AND TO_CHAR(TDL_DATE,'yyyy-fmmm')= ? " ;
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, todoListVO.getMember_no());
			pstmt.setString(2, todoListVO.getTdl_date());
			
			//System.out.println(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				// 각 값 list에 저장
				TodoListVO vo = new TodoListVO();
				vo.setTdl_no(rs.getInt("tdl_no"));
				vo.setTdl_date(rs.getString("tdl_date"));
				vo.setTdl_contents(rs.getString("tdl_contents"));
				vo.setTdl_category(rs.getString("tdl_category"));
				vo.setMember_no(rs.getInt("member_no"));
				   
				list.add(vo);
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
		}catch(Exception e) {e.printStackTrace();}
		return list;
	}
	
	
	
	
	public List<TodoListVO> read(TodoListVO vo1){
		
		List<TodoListVO> list = new ArrayList();
	
		try {
			con=dataFactory.getConnection();
			String query  = "SELECT * FROM T_TODOLIST  ";
				   query += " WHERE MEMBER_NO = ? ";
				   query += " AND TO_CHAR(TDL_DATE,'yyyy-fmmm-dd')=? ";
				   query += " ORDER BY TDL_NO ASC ";

			pstmt=con.prepareStatement(query);

			
			pstmt.setInt(1, vo1.getMember_no());
			pstmt.setString(2, vo1.getTdl_date());
			 
//			System.out.println("query : "+query);
//			System.out.println("vo1.getMember_no() : "+vo1.getMember_no());
//			System.out.println("vo1.getTdl_date() : "+vo1.getTdl_date());
			
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				
				int tdl_no = rs.getInt("tdl_no");
				String tdl_date = rs.getString("tdl_date");
				String tdl_contents = rs.getString("tdl_contents");
				String tdl_category = rs.getString("tdl_category");
				int member_no = rs.getInt("member_no");
				
				TodoListVO vo = new TodoListVO();
				vo.setTdl_no(tdl_no);
				vo.setTdl_date(tdl_date);
				vo.setTdl_contents(tdl_contents);
				vo.setTdl_category(tdl_category);
				vo.setMember_no(member_no);
				
				list.add(vo);
				
		
			}
			

			rs.close();
			pstmt.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("list size : "+ list.size());
		}
		return list;
	}
	
	
	
	
	
	public void add(TodoListVO vo) {
		
		 try {
			 
			 int tdl_no = vo.getTdl_no();
			 String tdl_date = vo.getTdl_date();
			 String tdl_contents = vo.getTdl_contents();
			 String tdl_category = vo.getTdl_category();
			 int member_no = vo.getMember_no();
		
			 con=dataFactory.getConnection();
			
			 String query = " insert into T_TODOLIST ";
					query += " (tdl_no,tdl_date,tdl_contents,tdl_category,member_no)";
			 		query += " values(T_TODOLIST_SEQUENCE.NEXTVAL,TO_DATE(?,'yyyy-fmmm-dd'),?,?,?) ";
			
//			System.out.println("PreparedStatement : "+ query);
			pstmt=con.prepareStatement(query);
			
			//insert문에 ?순서대로 세팅
//			pstmt.setInt(1, tdl_no);
			pstmt.setString(1, vo.getTdl_date() );
			pstmt.setString(2, vo.getTdl_contents());
			pstmt.setString(3, vo.getTdl_category());
			pstmt.setInt(4, vo.getMember_no());
			
			System.out.println("vo.getTdl_date() : "+vo.getTdl_date());
			System.out.println("vo.getTdl_contents() : "+vo.getTdl_contents());
			System.out.println("vo.getTdl_category() : "+vo.getTdl_category());
			System.out.println("vo.getMember_no() : " +vo.getMember_no());
			
			pstmt.executeUpdate();//회원정보를 테이블에 추가
			
			pstmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void del(TodoListVO vo) {
		
		try {
			con=dataFactory.getConnection();
			
			String query=" delete from t_todolist ";
			query	+=" where tdl_no = ? ";
			
			pstmt=con.prepareStatement(query);
			
			//첫번째 ?인 where tdl_no="id"인 애를 지움
			pstmt.setInt(1, vo.getTdl_no());
			int result = pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public void mod(TodoListVO vo) {

		try {

			con = dataFactory.getConnection();

			String 	query  = " UPDATE T_TODOLIST ";
					query += " SET TDL_CONTENTS = ?, TDL_CATEGORY = ? ";
					query += " WHERE TDL_NO = ? ";

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, vo.getTdl_contents());
			pstmt.setString(2, vo.getTdl_category());
			pstmt.setInt(3, vo.getTdl_no());
			
			System.out.println("getTdl_contents : "+vo.getTdl_contents());
			System.out.println("getTdl_category : "+vo.getTdl_category());
			System.out.println("getTdl_no : "+vo.getTdl_no());

			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}