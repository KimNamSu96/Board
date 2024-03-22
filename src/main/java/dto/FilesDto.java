package dto;

public class FilesDto {

	private int fno;
	private String filepath;
	private int no;
	private String username;
	
	public FilesDto() {}

	public FilesDto(int fno, String filepath, int no, String username) {
		this.fno = fno;
		this.filepath = filepath;
		this.no = no;
		this.username = username;
	}
	
	public FilesDto(String filepath, String username) {
		this.filepath = filepath;
		this.username = username;
	}

	public int getFno() {
		return fno;
	}

	public void setFno(int fno) {
		this.fno = fno;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
