package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.JWTOkens;
import dao.BoardDao;
import dao.FilesDao;
import dto.BoardDto;
import dto.FilesDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/board/View.do")
public class ViewServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDao dao = new BoardDao(getServletContext());
		String token = JWTOkens.getToken(req, req.getServletContext().getInitParameter("COOKIE-NAME"));
		Map tokenMap = JWTOkens.getTokenPayloads(token);
		req.setAttribute("username", tokenMap.get("username").toString());
		FilesDao filesDao = new FilesDao(getServletContext());
		String referer = req.getHeader("referer");	
		Map map = new HashMap();
		map.put("no", Integer.parseInt(req.getParameter("no")));
		map.put("referer", referer);
		BoardDto dto = dao.select(map);
		List<FilesDto> list = filesDao.getFileByNo(map);
		if(list != null) req.setAttribute("files", list);
		req.setAttribute("record",dto);
		req.getRequestDispatcher("/WEB-INF/board/View.jsp").forward(req, resp);
	}

}
