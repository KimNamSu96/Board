package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import config.JWTOkens;
import dao.MemberDao;
import dto.MemberDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.PageContext;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/member/Login.do")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String token=JWTOkens.getToken(req, req.getServletContext().getInitParameter("COOKIE-NAME"));
		if(token != "") {
			Map map = JWTOkens.getTokenPayloads(token);
		}
		req.getRequestDispatcher("/WEB-INF/member/Login.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDao dao = new MemberDao(getServletContext());
		Map<String,String> map = new HashMap();//사용자 임의 데이타 추가
		map.put("username", request.getParameter("username"));
		map.put("password", request.getParameter("password"));
		MemberDto member = dao.select(map);
		if(member != null) {
			Map<String,Object> payloads = new HashMap<>();
			payloads.put("password",map.get("password"));//추가적인 사용자 정보 저장
			payloads.put("username",map.get("username"));//추가적인 사용자 정보 저장
			long expirationTime = 1000 * 60 * 60;//토큰의 만료시간 설정(1시간)
			String token=JWTOkens.createToken(map.get("id"), payloads, expirationTime);
			Cookie cookie = new Cookie(getServletContext().getInitParameter("COOKIE-NAME"),token);
			System.out.println(token);
			cookie.setPath(request.getContextPath());
			response.addCookie(cookie);
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}else 
			response.sendRedirect(request.getContextPath()+"/member/Login.do");
	}

}
