package dto;

import java.sql.Date;

public class BoardDto {

	private int no;
	private String username;
	private String title;
	private String content;
	private int visitcount;
	private Date postdate;
	
	public BoardDto() {}

	public BoardDto(int no, String username, String title, String content, int visitcount) {
		this.no = no;
		this.username = username;
		this.title = title;
		this.content = content;
		this.visitcount = visitcount;
	}
	
	public BoardDto(int no, String username, String title, String content, int visitcount, Date postdate) {
		this.no = no;
		this.username = username;
		this.title = title;
		this.content = content;
		this.visitcount = visitcount;
		this.postdate = postdate;
	}
	
	public BoardDto(int no, String title, String content) {
		this.no = no;
		this.title = title;
		this.content = content;
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

	public int getVisitcount() {
		return visitcount;
	}

	public void setVisitcount(int visitcount) {
		this.visitcount = visitcount;
	}

	public Date getPostdate() {
		return postdate;
	}

	public void setPostdate(Date postdate) {
		this.postdate = postdate;
	}
	
}
