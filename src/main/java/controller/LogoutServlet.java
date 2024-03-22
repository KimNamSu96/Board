package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import config.JWTOkens;

@WebServlet("/member/Logout.do")
public class LogoutServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JWTOkens.removeToken(req, resp);
		resp.sendRedirect(req.getContextPath()+"/member/Login.do");
	}

}
