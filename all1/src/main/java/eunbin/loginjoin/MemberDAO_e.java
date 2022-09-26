package eunbin.loginjoin;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO_e {

    private Connection con;
    private PreparedStatement pstmt;
    private DataSource dataFactory;

    // 커넥션 풀
    public MemberDAO_e() {
        try {
            Context ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            dataFactory = (DataSource) envContext.lookup("jdbc/oracle0303");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 회원가입 (멤버 추가 메서드)
    public void addMember(MemberDTO_e dto) {
    	System.out.println("MemberDAO - addMember - 회원가입 시작");
        try {
            con = dataFactory.getConnection();
            String id = dto.getId();
            String pw = dto.getPw();
            String name = dto.getName();
            String nickname = dto.getNickname();
            String gender = dto.getGender();
            Date birth = dto.getBirth();
            String tel = dto.getTel();
            String email = dto.getEmail();
            int height = dto.getHeight();
            String pro_img = dto.getPro_img();
            
            String query = "insert into T_member";
            query += "(member_no, id, pw, name, nickname, gender, birth, tel, email, height, pro_img)";
            query += " values(T_MEMBER_SEQUENCE.NEXTVAL,";
            query += "'"+id+"', '"+pw+"', '"+name+"', '"+nickname+"', '"+gender+"', '";
            query += birth+"', '"+tel+"', '"+email+"', "+height+", '"+pro_img+"')";
            System.out.println(query);

            pstmt = con.prepareStatement(query);
            pstmt.executeUpdate();
            
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MemberDAO - addMember - 회원가입 종료");
    }
    
    // 로그인 체크
    public int loginMember(String id, String pw) {
    	System.out.println("MemberDAO - loginMember - 로그인 체크 시작");
    	int count = 0;
        try {
            con = dataFactory.getConnection();
            
            // 쿼리문 작성
            String query = "SELECT count(*) ";
            query += "FROM T_member ";
            query += "WHERE id = '"+id+"' ";
            query += "AND pw = '"+pw+"'";
            System.out.println(query);
            // 쿼리문 저장
            pstmt = con.prepareStatement(query);
            
            // rs의 첫번째 필드값 저장
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
            	count = rs.getInt(1);            	
            }
            
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MemberDAO - loginMember - 로그인 체크 완료");
        return count;
    }
    
    // 로그인한 회원정보 불러오기
    public MemberDTO_e load_login(String _id) {
    	System.out.println("MemberDAO - load_login - 로그인한 회원정보 불러오기 시작");
    	MemberDTO_e  dto = new MemberDTO_e();
        try {
            con = dataFactory.getConnection();
            
            // 쿼리문 작성
            String query = "SELECT * ";
            query += "FROM T_member ";
            query += "WHERE id = '"+_id+"'";
            System.out.println(query);

            // 쿼리문 저장
            pstmt = con.prepareStatement(query);
            
            // 결과값 불러오기
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	int member_no = rs.getInt("member_no");
            	String id = rs.getString("id");
                String pw = rs.getString("pw");
                String name = rs.getString("name");
                String nickname = rs.getString("nickname");
                String gender = rs.getString("gender");
                Date birth = rs.getDate("birth");
                String tel = rs.getString("tel");
                String email = rs.getString("email");
                int height = rs.getInt("height");
                String pro_img = rs.getString("pro_img");
                
                dto.setMember_no(member_no);
                dto.setId(id);
                dto.setPw(pw);
                dto.setName(name);
                dto.setNickname(nickname);
                dto.setGender(gender);
                dto.setBirth(birth);
                dto.setTel(tel);
                dto.setEmail(email);
                dto.setHeight(height);
                dto.setPro_img(pro_img);
            }
            
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MemberDAO - load_login - 로그인한 회원정보 불러오기 완료");
        return dto;
    }
    
    // 아이디 중복 체크
    public int idCheck(String _id) {
    	System.out.println("MemberDAO - idCheck - 아이디 중복 체크 시작");
    	int count = 0;
        try {
            con = dataFactory.getConnection();
            
            // 쿼리문 작성
            String query = "SELECT count(*) ";
            query += "FROM T_member ";
            query += "WHERE id = '"+_id+"' ";
            System.out.println(query);
            // 쿼리문 저장
            pstmt = con.prepareStatement(query);
            
            // rs의 첫번째 필드값 저장
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
            	count = rs.getInt(1);            	
            }
            
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MemberDAO - idCheck - 아이디 중복 체크 완료");
        return count;
    }
    
    // 닉네임 중복 체크
    public int nickCheck(String _nickname) {
    	System.out.println("MemberDAO - nickCheck - 닉네임 중복 체크 시작");
    	int count = 0;
        try {
            con = dataFactory.getConnection();
            
            // 쿼리문 작성
            String query = "SELECT count(*) ";
            query += "FROM T_member ";
            query += "WHERE nickname = '"+_nickname+"' ";
            System.out.println(query);
            // 쿼리문 저장
            pstmt = con.prepareStatement(query);
            
            // rs의 첫번째 필드값 저장
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
            	count = rs.getInt(1);            	
            }
            
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MemberDAO - nickCheck - 닉네임 중복 체크 완료");
        return count;
    }
    
    // 회원 정보 가져오기 - member_no 이용
    public MemberDTO_e load_member(int _member_no) {
    	System.out.println("MemberDAO - load_member - 회원 정보 가져오기 - member_no 이용 시작");
    	MemberDTO_e  dto = new MemberDTO_e();
        try {
            con = dataFactory.getConnection();
            
            // 쿼리문 작성
            String query = "SELECT * ";
            query += "FROM T_member ";
            query += "WHERE member_no = "+_member_no;
            System.out.println(query);

            // 쿼리문 저장
            pstmt = con.prepareStatement(query);
            
            // 결과값 불러오기
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	int member_no = rs.getInt("member_no");
            	String id = rs.getString("id");
                String pw = rs.getString("pw");
                String name = rs.getString("name");
                String nickname = rs.getString("nickname");
                String gender = rs.getString("gender");
                Date birth = rs.getDate("birth");
                String tel = rs.getString("tel");
                String email = rs.getString("email");
                int height = rs.getInt("height");
                String pro_img = rs.getString("pro_img");
                
                dto.setMember_no(member_no);
                dto.setId(id);
                dto.setPw(pw);
                dto.setName(name);
                dto.setNickname(nickname);
                dto.setGender(gender);
                dto.setBirth(birth);
                dto.setTel(tel);
                dto.setEmail(email);
                dto.setHeight(height);
                dto.setPro_img(pro_img);
            }
            
            rs.close();
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MemberDAO - load_member - 회원 정보 가져오기 - member_no 이용 완료");
        return dto;
    }
    
    // 회원정보 변경
    public void updateMember(MemberDTO_e dto) {
    	System.out.println("MemberDAO - updateMember - 회원정보 변경 시작");
        try {
            con = dataFactory.getConnection();
            String id = dto.getId();
            String pw = dto.getPw();
            String name = dto.getName();
            String nickname = dto.getNickname();
            String gender = dto.getGender();
            Date birth = dto.getBirth();
            String tel = dto.getTel();
            String email = dto.getEmail();
            int height = dto.getHeight();
            String pro_img = dto.getPro_img();
            
            String query = "update T_member";
            query += " set name = '"+name+"',"
            		+ " nickname = '"+nickname+"',"
            		+ " gender = '"+gender+"',"
            		+ " tel = '"+tel+"',"
            		+ " email = '"+email+"',"
            		+ " height = "+height;
            query += " where id = '"+id+"'";
            System.out.println(query);

            pstmt = con.prepareStatement(query);
            pstmt.executeUpdate();
            
            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("MemberDAO - updateMember - 회원정보 변경 종료");
    }
    
}