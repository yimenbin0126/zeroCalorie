package yoo.calender;

import java.sql.Date;

public class CheerMsgVO {
	
	private int CHR_NO;
	private String CHR_MSG;
	private Date CHR_TIME;
	private int FR_MEMBER_NO;
	private int TO_MEMBER_NO;
	private String NICKNAME; 
	private String ID;
	   

	public int getCHR_NO() {
		return CHR_NO;
	}
	public void setCHR_NO(int cHR_NO) {
		CHR_NO = cHR_NO;
	}
	public String getCHR_MSG() {
		return CHR_MSG;
	}
	public void setCHR_MSG(String cHR_MSG) {
		CHR_MSG = cHR_MSG;
	}
	public Date getCHR_TIME() {
		return CHR_TIME;
	}
	public void setCHR_TIME(Date cHR_TIME) {
		CHR_TIME = cHR_TIME;
	}
	public int getFR_MEMBER_NO() {
		return FR_MEMBER_NO;
	}
	public void setFR_MEMBER_NO(int fR_MEMBER_NO) {
		FR_MEMBER_NO = fR_MEMBER_NO;
	}
	
	public int getTO_MEMBER_NO() {
		return TO_MEMBER_NO;
	}
	public void setTO_MEMBER_NO(int tO_MEMBER_NO) {
		TO_MEMBER_NO = tO_MEMBER_NO;
	}
	
	public String getNICKNAME() {
		return NICKNAME;
	}
	public void setNICKNAME(String nICKNAME) {
		NICKNAME = nICKNAME;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	   
}
