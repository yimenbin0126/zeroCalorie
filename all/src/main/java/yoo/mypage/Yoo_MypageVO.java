package yoo.mypage;

import java.sql.Date;

public class Yoo_MypageVO {
	
	private int WEIGHT_NO;
	private int CURRENT_WEIGHT;
	private int TARGET_WEIGHT;	
	private Date WEIGHT_DATE;
	private int MEMBER_NO;
	
	
	public int getWEIGHT_NO() {
		return WEIGHT_NO;
	}
	public void setWEIGHT_NO(int wEIGHT_NO) {
		WEIGHT_NO = wEIGHT_NO;
	}
	public int getCURRENT_WEIGHT() {
		return CURRENT_WEIGHT;
	}
	public void setCURRENT_WEIGHT(int cURRENT_WEIGHT) {
		CURRENT_WEIGHT = cURRENT_WEIGHT;
	}
	public int getTARGET_WEIGHT() {
		return TARGET_WEIGHT;
	}
	public void setTARGET_WEIGHT(int tARGET_WEIGHT) {
		TARGET_WEIGHT = tARGET_WEIGHT;
	}
	public Date getWEIGHT_DATE() {
		return WEIGHT_DATE;
	}
	public void setWEIGHT_DATE(Date wEIGHT_DATE) {
		WEIGHT_DATE = wEIGHT_DATE;
	}
	public int getMEMBER_NO() {
		return MEMBER_NO;
	}
	public void setMEMBER_NO(int mEMBER_NO) {
		MEMBER_NO = mEMBER_NO;
	}


}
