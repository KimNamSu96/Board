package config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class MyContextListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("웹 어플리케이션이 시작 되었습니다");
		System.out.println("서버 정보:"+sce.getServletContext().getServerInfo());
		System.out.println("컨텍스트 루트:"+sce.getServletContext().getContextPath());
		try {
			Context ctx = new InitialContext();
			DataSource source=(DataSource)ctx.lookup(sce.getServletContext().getInitParameter("JNDI-ROOT")+"/ICTUSER");
			sce.getServletContext().setAttribute("DataSource", source);
		}
		catch(NamingException e) {e.printStackTrace();}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("웹 어플리케이션이 종료 되었습니다");
	}

	
	
}
