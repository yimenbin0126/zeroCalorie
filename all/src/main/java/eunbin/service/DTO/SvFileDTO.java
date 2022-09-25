package eunbin.service.DTO;

public class SvFileDTO {
	private int bno;
	private String filename;
	private int server_filename;
	private int path;
	private String file_extension;
	private String file_date;
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getServer_filename() {
		return server_filename;
	}
	public void setServer_filename(int server_filename) {
		this.server_filename = server_filename;
	}
	public int getPath() {
		return path;
	}
	public void setPath(int path) {
		this.path = path;
	}
	public String getFile_extension() {
		return file_extension;
	}
	public void setFile_extension(String file_extension) {
		this.file_extension = file_extension;
	}
	public String getFile_date() {
		return file_date;
	}
	public void setFile_date(String file_date) {
		this.file_date = file_date;
	}
	
	@Override
	public String toString() {
		return "SvFileDTO [bno=" + bno + ", filename=" + filename + ", server_filename=" + server_filename + ", path="
				+ path + ", file_extension=" + file_extension + ", file_date=" + file_date + "]";
	}
}
