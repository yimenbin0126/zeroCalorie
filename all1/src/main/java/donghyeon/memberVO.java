package donghyeon;

public class memberVO {
	private String id;
	private String pwd;
	private String name;
	private String nickname;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
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
	public void setNickname(String email) {
		this.nickname = email;
	}
	@Override
	public String toString() {
		return "memberVO [id=" + id + ", pwd=" + pwd + ", name=" + name + ", nickname=" + nickname + "]";
	}

	
	
}
