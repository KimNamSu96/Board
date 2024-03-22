package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import config.JWTOkens;
import dao.BoardDao;
import dto.BoardDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditBoardServlet
 */
@WebServlet("/board/Edit.do")
public class EditBoardServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDao dao = new BoardDao(getServletContext());
		Map map = new HashMap();
		map.put("no", req.getParameter("no"));
		BoardDto dto = dao.select(map);
		req.setAttribute("record", dto);
		req.getRequestDispatcher("/WEB-INF/board/Write.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDao dao = new BoardDao(getServletContext());
		BoardDto dto = null;
		Map<String,String[]> map = req.getParameterMap();
		String token = JWTOkens.getToken(req, req.getServletContext().getInitParameter("COOKIE-NAME"));
		Map tokenMap = JWTOkens.getTokenPayloads(token);
		if(map.get("username")[0].toString().equals(tokenMap.get("username").toString())) {
			dto = new BoardDto(Integer.parseInt(map.get("no")[0].toString()),
						map.get("title")[0].toString(),	map.get("content")[0].toString());
			dao.update(dto);
		}
		resp.sendRedirect(req.getContextPath()+"/board/View.do?no="+dto.getNo());
	}

}
