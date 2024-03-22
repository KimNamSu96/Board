package controller;

import java.io.IOException;
import java.util.Map;

import config.JWTOkens;
import dao.MemberDao;
import dto.MemberDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member/Edit.do")
public class EditMemberServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDao dao = new MemberDao(getServletContext());
		String token = JWTOkens.getToken(req, req.getServletContext().getInitParameter("COOKIE-NAME"));
		Map map = JWTOkens.getTokenPayloads(token);
		MemberDto dto = dao.select(map);
		req.setAttribute("member", dto);
		req.getRequestDispatcher("/WEB-INF/member/Join.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String[]> map = req.getParameterMap();
		MemberDao dao = new MemberDao(getServletContext());
		MemberDto member = new MemberDto(map.get("username")[0],map.get("password")[0],
							map.get("gender")[0], map.get("inters"), map.get("education")[0],
							map.get("selfintroduce")[0]);
		dao.update(member);
		resp.sendRedirect(req.getContextPath()+"/index.jsp");
	}

}
