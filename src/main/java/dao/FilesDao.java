package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import config.DBConnection;
import dto.FilesDto;
import dto.ListPagingData;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import service.DaoService;

public class FilesDao implements DaoService<FilesDto>{

	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	public FilesDao(ServletContext context) {
		DBConnection dbConnection = new DBConnection(context);
		conn = dbConnection.conn;
	}

	@Override
	public void close() {
		try {
			if(rs != null) rs.close();
			if(psmt != null) psmt.close();
			if(conn != null) conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getTotalRecordCount(Map map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(FilesDto dto) {
		String sql = "INSERT INTO files VALUES(SEQ_FILES.NEXTVAL,?,?,?)";
		int affected = 0;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getFilepath());
			psmt.setInt(2, dto.getNo());
			psmt.setString(3, dto.getUsername());
			affected = psmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return affected;
	}

	@Override
	public FilesDto select(Map map) {
		return null;
	}

	@Override
	public int update(FilesDto dto) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(FilesDto dto) {
		String sql = "DELETE files WHERE no=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, dto.getNo());
			psmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return 0;
	}

	@Override
	public ListPagingData<FilesDto> selectAll(Map map, HttpServletRequest req, int nowPage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FilesDto> getFileByNo(Map map) {
		String sql = "SELECT * FROM files WHERE no=?";
		List<FilesDto> list = new ArrayList<>();
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(map.get("no").toString()));
			rs = psmt.executeQuery();
			while(rs.next()) {
				FilesDto dto = new FilesDto(rs.getInt(1),rs.getString(2),
									rs.getInt(3),rs.getString(4));
				list.add(dto);
			}
			return list;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return null;
	}
	
}
