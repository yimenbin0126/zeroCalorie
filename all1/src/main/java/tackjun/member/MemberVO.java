package tackjun.member;

import java.sql.Date;

public class MemberVO {
	private int member_no;
	private String id;
	private String pw;
	private String name;
	private String nickname;
	private String gender;
	private Date birth;
	private String tel;
	private String email;
	private int height;
	private String pro_img;
	
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getPro_img() {
		return pro_img;
	}
	public void setPro_img(String pro_img) {
		this.pro_img = pro_img;
	}
	
	@Override
	public String toString() {
		return "MemberDTO [member_no=" + member_no + ", id=" + id + ", pw=" + pw + ", name=" + name + ", nickname="
				+ nickname + ", gender=" + gender + ", birth=" + birth + ", tel=" + tel + ", email=" + email
				+ ", height=" + height + ", pro_img=" + pro_img + "]";
	}
}