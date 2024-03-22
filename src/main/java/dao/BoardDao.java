package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import config.DBConnection;
import config.PagingUtil;
import dto.BoardDto;
import dto.FilesDto;
import dto.ListPagingData;
import dto.ListPagingDataBuilder;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import service.DaoService;

public class BoardDao implements DaoService<BoardDto>{

	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	public BoardDao(ServletContext context) {
		DBConnection dbConnection = new DBConnection(context);
		conn = dbConnection.conn;
	}

	@Override
	public void close() {
		try {
			if(rs != null) rs.close();
			if(psmt != null) psmt.close();
			if(conn != null) conn.close();
		}catch(Exception e) {e.printStackTrace();}
	}

	@Override
	public int getTotalRecordCount(Map map) {
		String sql = "SELECT COUNT(*) FROM bbs";
		int totalCount = 0;
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			if(rs.next())totalCount = rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return totalCount;
	}

	@Override
	public int insert(BoardDto dto) {
		String sql = "INSERT INTO bbs VALUES(SEQ_BBS.NEXTVAL,?,?,?,0,SYSDATE)";
		int no = 0;
		try {
			psmt = conn.prepareStatement(sql, new int[] {1});
			psmt.setString(1, dto.getUsername());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			psmt.executeUpdate();
			rs = psmt.getGeneratedKeys();
			if(rs.next()) no=rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return no;
	}

	@Override
	public BoardDto select(Map map) {
		if(map.get("referer") != null) {
			if(map.get("referer").toString().endsWith("ListPage.do")) {
				try {
					psmt = conn.prepareStatement("UPDATE bbs SET visitcount=visitcount+1 WHERE no=?");
					psmt.setInt(1, Integer.parseInt(map.get("no").toString()));
					psmt.executeUpdate();
				}catch(SQLException e) {}
			}
		}
		String sql = "SELECT * FROM bbs WHERE no=?";
		BoardDto dto = null;
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, Integer.parseInt(map.get("no").toString()));
			rs = psmt.executeQuery();
			if(rs.next()) {
				dto = new BoardDto(rs.getInt(1),rs.getString(2),rs.getString(3),
						rs.getString(4),rs.getInt(5),rs.getDate(6)); 
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return dto;
	}

	@Override
	public int update(BoardDto dto) {
		String sql = "UPDATE bbs SET title=?,content=? WHERE no=?";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setInt(3, dto.getNo());
			psmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return 0;
	}

	@Override
	public int delete(BoardDto dto) {
		String sql = "DELETE bbs WHERE no=?";
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
	public ListPagingData<BoardDto> selectAll(Map map, HttpServletRequest req, int nowPage) {
		List<BoardDto> dtoList = new ArrayList<>();
		String sql="SELECT * FROM (SELECT b.*, RANK() OVER (ORDER BY no DESC) AS no_rank FROM bbs b";
				//검색시 추가 시작
				if(map.get("searchColumn") !=null) {
					sql+=" WHERE "+map.get("searchColumn") + " LIKE '%"+map.get("searchWord")+"%'";
				}
				sql+= ") "
				+ "		WHERE no_rank BETWEEN ? AND ? ";
	
		try {
			psmt = conn.prepareStatement(sql);
			//페이징을 위한 시작 및 종료 Rank설정
			psmt.setString(1, map.get(PagingUtil.START).toString());
			psmt.setString(2, map.get(PagingUtil.END).toString());
			rs=psmt.executeQuery();
			while(rs.next()) {
				BoardDto dto = new BoardDto();
				dto.setNo(rs.getInt(1));
				dto.setUsername(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setVisitcount(rs.getInt(5));
				dto.setPostdate(rs.getDate(6));
				dtoList.add(dto);
			}
			String pagingString=PagingUtil.pagingBootStrapStyle(Integer.parseInt(map.get(PagingUtil.TOTAL_RECORD_COUNT).toString()),
															Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString()),
															Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString()),
															Integer.parseInt(map.get(PagingUtil.NOWPAGE).toString()),
															req.getContextPath()+"/board/ListPage.do?");
			ListPagingData<BoardDto> list = new ListPagingDataBuilder()
										.records(dtoList)
										.map(map)
										.pagingString(pagingString)
										.builder();
			return list;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return null;
	}

	@Override
	public List<FilesDto> getFileByNo(Map map) {
		// TODO Auto-generated method stub
		return null;
	}

}
