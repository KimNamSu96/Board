package controller;

import java.io.IOException;
import java.util.Map;

import dao.MemberDao;
import dto.MemberDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/member/Join.do")
public class JoinServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/member/Join.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> map = request.getParameterMap();
		MemberDao dao = new MemberDao(getServletContext());
		String inters = String.join(", ", map.get("inters"));
		MemberDto member = new MemberDto(map.get("username")[0],map.get("password")[0],
							map.get("gender")[0], map.get("inters"), map.get("education")[0],
							map.get("selfintroduce")[0]);
		dao.insert(member);
		request.getRequestDispatcher("/WEB-INF/member/Login.jsp").forward(request, response);
	}

}
