package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.PagingUtil;
import dao.BoardDao;
import dto.BoardDto;
import dto.ListPagingData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/board/ListPage.do")
public class ListServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao(getServletContext());
		//
		Map map = new HashMap();
		String searchColumn = request.getParameter("searchColumn");
		String searchWord = request.getParameter("searchWord");
		
		if(searchWord !=null && searchWord.length() !=0){
			map.put("searchColumn",searchColumn);
			map.put("searchWord",searchWord);
		}
		
		PagingUtil.setMapForPaging(map, dao, request);
		int totalRecordCount=Integer.parseInt(map.get(PagingUtil.TOTAL_RECORD_COUNT).toString());
		int pageSize=Integer.parseInt(map.get(PagingUtil.PAGE_SIZE).toString());
		int blockPage=Integer.parseInt(map.get(PagingUtil.BLOCK_PAGE).toString());
		int nowPage=Integer.parseInt(map.get(PagingUtil.NOWPAGE).toString());
		int totalPage=Integer.parseInt(map.get(PagingUtil.TOTAL_PAGE).toString());
		ListPagingData<BoardDto> list = dao.selectAll(map, request, nowPage);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/board/List.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
