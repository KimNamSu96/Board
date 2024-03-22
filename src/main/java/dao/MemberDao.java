package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import config.DBConnection;
import dto.FilesDto;
import dto.ListPagingData;
import dto.MemberDto;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import service.DaoService;

public class MemberDao implements DaoService<MemberDto>{

	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	public MemberDao(ServletContext context) {
		DBConnection dbConnection = new DBConnection(context);
		conn = dbConnection.conn;
	}

	@Override
	public void close() {
		try {
			if(rs != null) rs.close();
			if(conn!=null) conn.close();
			if(psmt != null) psmt.close();
		}catch(Exception e) {e.printStackTrace();}
	}

	@Override
	public int getTotalRecordCount(Map map) {
		return 0;
	}

	@Override
	public int insert(MemberDto dto) {
		String sql = "INSERT INTO member VALUES(?,?,?,?,?,?,SYSDATE)";
		int affected = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getUsername());
			psmt.setString(2, dto.getPassword());
			psmt.setString(3, dto.getGender());
			psmt.setString(4, String.join(", ", dto.getInters()));
			psmt.setString(5, dto.getEducation());
			psmt.setString(6, dto.getSelfintroduce());
			affected = psmt.executeUpdate();
			System.out.println("입력:"+affected);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return affected;
	}
	
	@Override
	public MemberDto select(Map map) {
		String sql = "SELECT * FROM member WHERE username=? AND password=?";
		MemberDto member = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, map.get("username").toString());
			psmt.setString(2, map.get("password").toString());
			rs = psmt.executeQuery();
			if(rs.next())
				member = new MemberDto(rs.getString(1), rs.getString(2), rs.getString(3), 
					rs.getString(4).split(", "), rs.getString(5), rs.getString(6));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return member;
	}	

	@Override
	public int update(MemberDto dto) {
		String sql = "UPDATE member SET password=?, gender=?, inters=?, education=?, selfintroduce=? WHERE username=?";
		int affected = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getPassword());
			psmt.setString(2, dto.getGender());
			psmt.setString(3, String.join(", ", dto.getInters()));
			psmt.setString(4, dto.getEducation());
			psmt.setString(5, dto.getSelfintroduce());
			psmt.setString(6, dto.getUsername());
			affected = psmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return affected;
	}

	@Override
	public int delete(MemberDto dto) {
		String sql = "DELETE member WHERE username=?";
		int affected = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getUsername());
			affected = psmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return affected;
	}

	@Override
	public ListPagingData<MemberDto> selectAll(Map map, HttpServletRequest req, int nowPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FilesDto> getFileByNo(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

}
