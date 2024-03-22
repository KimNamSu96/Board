package filter;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import config.JWTOkens;
/**
 * Servlet Filter implementation class AuthenticationFilter
 * New->Filter로 생성
 * web.xml에 자동으로 <filter>태그 등록된다
 * 1.@WebFilter추가(web.xml에 등록된 <filter>태그 삭제
 * 2.extends HttpFilter 삭제(클래스 생성시 슈퍼클래스를 지정안하면 됨)
 * 3.doFilter()만 남긴다
 * 
 * 
 * 회원제 게시판(bbs08)의 각 JSP페이지의 인증여부용 판단을 위한
 * IsMember.jsp 인클루드 처리한 거를 필터로 교체하자
 * 
 * 인증여부 판단 코드 추가후에는 각 JSP페이지의 IsMember.jsp 인클루드 처리한 거 주석처리
 * 
 */
@WebFilter("/board/*")
public class AuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//사전작업으로 로그인 여부 먼저 판단
		HttpServletRequest req =(HttpServletRequest)request;
		
		//토큰기반 인증일때
		String token=JWTOkens.getToken(req, req.getServletContext().getInitParameter("COOKIE-NAME"));
		boolean isAuthenticated=JWTOkens.verifyToken(token);
		if(!isAuthenticated) {
			//방법1-브라우저로 HTML태그를 응답
			HttpServletResponse resp=(HttpServletResponse)response;
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out= resp.getWriter();
			out.println("<script>");
			out.println("alert('로그인이 필요한 서비스입니다')");
			out.println("location.replace('"+req.getContextPath()+"/member/Login.do')");
			out.println("</script>");
			return;
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
		System.out.println("필요한 사후 작업을 작성하세요");
	}/////

}
