package tackjun.community.board;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;

public class ArticleVO {
	private int articleNO;
	private int parentNO;
	private String title;
	private String content;
	private String imageFileName;
	private int member_no;
	private Date writeDate;
	
	public ArticleVO() {
		
	}


	public ArticleVO(int articleNO, int parentNO, String title, String content, String imageFileName,
			int member_no) {
		super();
		this.articleNO = articleNO;
		this.parentNO = parentNO;
		this.title = title;
		this.content = content;
		this.imageFileName = imageFileName;
		this.member_no = member_no;
	}


	public int getArticleNO() {
		return articleNO;
	}


	public void setArticleNO(int articleNO) {
		this.articleNO = articleNO;
	}


	public int getParentNO() {
		return parentNO;
	}


	public void setParentNO(int parentNO) {
		this.parentNO = parentNO;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	

	public String getImageFileName() {
		try {
			if (imageFileName != null && imageFileName.length() != 0) {
				imageFileName = URLDecoder.decode(imageFileName, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		try {
			if(imageFileName!=null && imageFileName.length()!=0) {
				this.imageFileName = URLEncoder.encode(imageFileName, "UTF-8");  //파일이름에 특수문자가 있을 경우 인코딩합니다.
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public int getMember_no() {
		return member_no;
	}


	public void setMember_no(int member_no) {
		this.member_no = member_no;
	}


	public Date getWriteDate() {
		return writeDate;
	}


	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	
	
	
	
	

}