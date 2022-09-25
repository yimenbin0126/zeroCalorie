package eunbin.service.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import eunbin.loginjoin.MemberDAO_e;
import eunbin.loginjoin.MemberDTO_e;
import eunbin.service.DTO.ServiceDTO;

public class ServiceDAO {

	MemberDAO_e m_dao = new MemberDAO_e();
	
    private Connection con;
    private PreparedStatement pstmt;
    private DataSource dataFactory;

    // 커넥션 풀
    public ServiceDAO() {
        try {
            Context ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            dataFactory = (DataSource) envContext.lookup("jdbc/oracle0303");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	// 특정 게시판 목록들 불러오기 - 전체
	public List<ServiceDTO> board_All(String _sv_type) {
		System.out.println("ServiceDAO - board_All - 특정 게시판 목록들 불러오기 - 전체 시작");
		// 게시물 타입으로 게시물 번호들 가져오기
		List<ServiceDTO> list_sv = new ArrayList<ServiceDTO>();

		// 게시물 번호를 순서로, 게시물 타입안의 게시물들 나열하기
		try {
			con = dataFactory.getConnection();

			// 쿼리문 작성
			String query = "SELECT * ";
			query += "FROM sv_board ";
			query += "WHERE sv_type = '" + _sv_type + "'";
			query += "ORDER BY bno desc";

			// 쿼리문 저장
			pstmt = con.prepareStatement(query);
			// 결과값 불러오기
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ServiceDTO dto = new ServiceDTO();

				int bno = rs.getInt("bno");
				String sv_type = rs.getString("sv_type");
				int group_origin = rs.getInt("group_origin");
				int group_order = rs.getInt("group_order");
				String admin_type = rs.getString("admin_type");
				String nickname = rs.getString("nickname");
				String title = rs.getString("title");
				String description = rs.getString("description");
				Date create_time = rs.getDate("create_time");
				int heart = rs.getInt("heart");
				int view_no = rs.getInt("view_no");
				int member_no = rs.getInt("member_no");

				dto.setBno(bno);
				dto.setSv_type(sv_type);
				dto.setGroup_origin(group_origin);
				dto.setGroup_order(group_order);
				dto.setAdmin_type(admin_type);
				dto.setNickname(nickname);
				dto.setTitle(title);
				dto.setDescription(description);
				dto.setCreate_time(create_time);
				dto.setHeart(heart);
				dto.setView_no(view_no);
				dto.setMember_no(member_no);

				list_sv.add(dto);
			}
			
			rs.close();
            pstmt.close();
            con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ServiceDAO - board_All - 특정 게시판 목록들 불러오기 - 전체 끝");
		return list_sv;
	}
    
    // 타입별 게시물 번호들 불러오기
    public List board_bno_All(String _sv_type) {
    	System.out.println("ServiceDAO - board_bno_All - 타입별 게시물 번호들 불러오기 시작");
    	ServiceDTO  dto = new ServiceDTO();
    	List list = new ArrayList();
    	int bno = 0;
        try {
            con = dataFactory.getConnection();
            
            // 쿼리문 작성
            String query = "SELECT bno ";
            query += "FROM sv_board ";
            query += "WHERE sv_type = '"+_sv_type+"'";
            System.out.println(query);
            
            // 쿼리문 저장
            pstmt = con.prepareStatement(query);
            // 결과값 불러오기
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData(); // 컬럼의 정보
            int count = rsmd.getColumnCount(); //컬럼의 갯수
            
            while(rs.next()) {
            	for(int i=1; i<=count; i++) {
            		bno = rs.getInt(i);
            		list.add(bno);
            	}
            }
            
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ServiceDAO - board_bno_All - 타입별 게시물 번호들 불러오기 끝");
        return list;
    }
    
    // 특정 게시물 불러오기 - 번호 (detail 상세보기)
    public ServiceDTO board_one(int _bno) {
    	System.out.println("ServiceDAO - board_one - 특정 게시물 불러오기 - 부분 (detail 상세보기) 시작");
    	// 게시물 타입, 게시물그룹 대표번호로 게시물 번호 얻기
    	ServiceDTO  dto = new ServiceDTO();
        try {
            con = dataFactory.getConnection();
            
            // 쿼리문 작성
            String query = "SELECT * ";
            query += "FROM sv_board ";
            query += "WHERE bno = '"+_bno+"'";
            System.out.println(query);

            // 쿼리문 저장
            pstmt = con.prepareStatement(query);
            
            // 결과값 불러오기
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	int bno = rs.getInt("bno");
            	String sv_type = rs.getString("sv_type");
            	int group_origin = rs.getInt("group_origin");
            	int group_order = rs.getInt("group_order");
            	String admin_type = rs.getString("admin_type");
                String nickname = rs.getString("nickname");
                String title = rs.getString("title");
                String description = rs.getString("description");
                Date create_time = rs.getDate("create_time");
                int heart = rs.getInt("heart");
                int view_no = rs.getInt("view_no");
                int member_no = rs.getInt("member_no");
                
                dto.setBno(bno);
                dto.setSv_type(sv_type);
                dto.setGroup_origin(group_origin);
                dto.setGroup_order(group_order);
                dto.setAdmin_type(admin_type);
                dto.setNickname(nickname);
                dto.setTitle(title);
                dto.setDescription(description);
                dto.setCreate_time(create_time);
                dto.setHeart(heart);
                dto.setView_no(view_no);
                dto.setMember_no(member_no);
            }
            
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ServiceDAO - board_one - 특정 게시물 불러오기 - 부분 (detail 상세보기) 끝");
        return dto;
    }
    
    // 게시물 번호 가져오기 - 개별
    public int board_bno(String _sv_type, int _group_origin) {
    	System.out.println("ServiceDAO - board_bno - 게시물 번호 가져오기 - 개별 시작");
    	// 게시물 타입, 게시물그룹 대표번호로 게시물 번호 얻기
    	int bno = 0;
        try {
            con = dataFactory.getConnection();
            
            // 쿼리문 작성
            String query = "SELECT bno ";
            query += "FROM sv_board ";
            query += "WHERE sv_type = '"+_sv_type+"'";
            query += "AND group_origin = '"+_group_origin+"'";
            System.out.println(query);

            // 쿼리문 저장
            pstmt = con.prepareStatement(query);
            
            // 결과값 불러오기
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	bno = rs.getInt(1);
			}
            
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ServiceDAO - board_bno - 게시물 번호 가져오기 - 개별 끝");
        return bno;
    }
    
    // 글 작성 - 최초
    public void board_write(MemberDTO_e m_dto, String _sv_type, String _title, String _description) {
    	System.out.println("ServiceDAO - board_write - 글 작성 - 최초 시작");
    	ServiceDTO  s_dto = new ServiceDTO();
    	// 작성 시간
    	java.util.Date date = new java.util.Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = simpleDateFormat.format(date);
		java.sql.Date date_re = java.sql.Date.valueOf(formattedDate);
    	
    	// 관리자
        try {
            con = dataFactory.getConnection();
            
            // 쿼리문 작성
            String query = "INSERT INTO sv_board ";
            query += "(bno, sv_type, group_origin, group_order, admin_type, nickname";
            query += ", title, description, create_time, heart, view_no, member_no) ";
            query += "VALUES (sv_bno_SEQUENCE.NEXTVAL, '";
            query += _sv_type+"', ";
            query += "1, ";
            query += "1, '";
            query += board_admin_type(m_dto.getId())+"', '";
            query += m_dto.getNickname()+"', '";
            query += _title+"', '";
            query += _description+"', '";
            query += date_re+"', ";
            query += "0, ";
            query += "0, ";
            query += m_dto.getMember_no()+")";
            System.out.println(query);

            // 쿼리문 저장
            pstmt = con.prepareStatement(query);
            pstmt.executeUpdate(); 
            
            pstmt.close();
            con.close();
            System.out.println("board_write");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ServiceDAO - board_write - 글 작성 - 최초 끝");
    }
    
    // 글 삭제
    public void board_delete(int _bno) {
    	System.out.println("ServiceDAO - board_delete - 글 삭제 시작");
    	ServiceDTO  dto = new ServiceDTO();
        try {
            con = dataFactory.getConnection();
            
            // 쿼리문 작성
            String query = "DELETE ";
            query += "FROM sv_board ";
            query += "WHERE bno = '"+_bno+"'";
            System.out.println(query);

            // 쿼리문 저장
            pstmt = con.prepareStatement(query);
            pstmt.executeUpdate(); 
            
            pstmt.close();
            con.close();
            System.out.println("board_delete");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ServiceDAO - board_delete - 글 삭제 끝");
    }
    
    // 글 수정
    public void board_fix(int _bno, String _title, String _description) {
    	System.out.println("ServiceDAO - board_fix - 글 수정 시작");
        try {
            con = dataFactory.getConnection();
            
            // 쿼리문 작성
            String query = "UPDATE sv_board ";
            query += "SET title ='"+_title+"', ";
            query += "description ='"+_description+"' ";
            query += "WHERE bno = '"+_bno+"'";
            System.out.println(query);

            // 쿼리문 저장
            pstmt = con.prepareStatement(query);
            pstmt.executeUpdate(); 
            
            pstmt.close();
            con.close();
            System.out.println("board_fix");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ServiceDAO - board_fix - 글 수정 끝");
    }
    
    // 관리자 여부 반환 (admin == 관리자 아이디)
    public String board_admin_type(String id) {
    	System.out.println("ServiceDAO - board_admin_type - 관리자 여부 반환 시작");
    	if(id.equals("admin")) {
    		System.out.println("관리자 여부 : 관리자");
    		System.out.println("ServiceDAO - board_admin_type - 관리자 여부 반환 끝");
    		return "Y";
    	} else {
    		System.out.println("관리자 여부 : 회원");
    		System.out.println("ServiceDAO - board_admin_type - 관리자 여부 반환 끝");
    		return "N";
    	}
    }
}