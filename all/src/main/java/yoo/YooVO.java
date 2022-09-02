package yoo;

import java.sql.Date;

public class YooVO {
	
	private int no;
	private String id;
	private int current_weight;
	private int target_weight;	
	private Date record_date;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCurrent_weight() {
		return current_weight;
	}
	public void setCurrent_weight(int current_weight) {
		this.current_weight = current_weight;
	}
	public int getTarget_weight() {
		return target_weight;
	}
	public void setTarget_weight(int target_weight) {
		this.target_weight = target_weight;
	}
	public Date getRecord_date() {
		return record_date;
	}
	public void setRecord_date(Date record_date) {
		this.record_date = record_date;
	}
	
	

}
