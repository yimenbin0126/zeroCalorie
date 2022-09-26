package eunbin.service.DTO;

import java.sql.Date;

// 게시판 테이블
public class ServiceDTO {
	private int bno;
	private String sv_type;
	private int group_origin;
	private int group_order;
	private String admin_type;
	private String nickname;
	private String title;
	private String description;
	private Date create_time;
	private int heart;
	private int view_no;
	private int member_no;
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getSv_type() {
		return sv_type;
	}
	public void setSv_type(String sv_type) {
		this.sv_type = sv_type;
	}
	public int getGroup_origin() {
		return group_origin;
	}
	public void setGroup_origin(int group_origin) {
		this.group_origin = group_origin;
	}
	public int getGroup_order() {
		return group_order;
	}
	public void setGroup_order(int group_order) {
		this.group_order = group_order;
	}
	public String getAdmin_type() {
		return admin_type;
	}
	public void setAdmin_type(String admin_type) {
		this.admin_type = admin_type;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public int getHeart() {
		return heart;
	}
	public void setHeart(int heart) {
		this.heart = heart;
	}
	public int getView_no() {
		return view_no;
	}
	public void setView_no(int view_no) {
		this.view_no = view_no;
	}
	public int getMember_no() {
		return member_no;
	}
	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}
	
	@Override
	public String toString() {
		return "ServiceDTO [bno=" + bno + ", sv_type=" + sv_type + ", group_origin=" + group_origin + ", group_order="
				+ group_order + ", admin_type=" + admin_type + ", nickname=" + nickname + ", title=" + title
				+ ", description=" + description + ", create_time=" + create_time + ", heart=" + heart + ", view_no="
				+ view_no + ", member_no=" + member_no + "]";
	}
}
