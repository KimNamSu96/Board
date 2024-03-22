package controller;

import java.io.IOException;
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

@WebServlet("/board/Delete.do")
public class DeleteBoardServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao(getServletContext());
		FilesDao filesDao = new FilesDao(getServletContext());
		int no = Integer.parseInt(request.getParameter("no"));
		String username = request.getParameter("username");
		String token = JWTOkens.getToken(request, request.getServletContext().getInitParameter("COOKIE-NAME"));
		Map map = JWTOkens.getTokenPayloads(token);
		if(map.get("username").toString().equals(username)) {
			FilesDto filesDto = new FilesDto();
			filesDto.setNo(no);
			BoardDto dto = new BoardDto();
			dto.setNo(no);
			filesDao.delete(filesDto);
			dao.delete(dto);
		}
		response.sendRedirect(request.getContextPath()+"/board/ListPage.do");
	}

}
