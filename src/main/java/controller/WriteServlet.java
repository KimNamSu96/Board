package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import config.FileUtils;
import config.JWTOkens;
import dao.BoardDao;
import dao.FilesDao;
import dto.BoardDto;
import dto.FilesDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/board/Write.do")
@MultipartConfig(maxFileSize = 1024 * 500,maxRequestSize = 1024 * 500 *5)
public class WriteServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/board/Write.jsp").forward(request, response);
	}
		
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<FilesDto> list = new ArrayList();
		BoardDao dao = new BoardDao(getServletContext());
		String token = JWTOkens.getToken(req, req.getServletContext().getInitParameter("COOKIE-NAME"));
		Map map = JWTOkens.getTokenPayloads(token);
		BoardDto dto = new BoardDto();
		String username = map.get("username").toString();
		dto.setUsername(username);
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		
		try {
			//파일을 저장할 서버의 물리적 경로
			String saveDirectory=req.getServletContext().getRealPath("/upload");
			//getParts():현재 요청(multipart/form-data)과 관련된 모든 Part컴포넌트들을 컬렉션으로 반환
			Collection<Part> parts= req.getParts();//파일용량 초과시 에러			
			//업로드한 파일 정보를 담기 위한 컬렉션 선언
			//파일 하나의 정보를 저장할 맵:Map<String, String>
			List<Map<String, String>> files = new Vector<>();
			//upload폴더 생성(폴더가 없을경우)
			File folder = new File(saveDirectory);
			if(!folder.exists()) {
				folder.mkdir();
				System.out.println("폴더생성");
			}
			
			for(Part part:parts) {			
				if(part.getContentType() != null) {
					if (part.getSubmittedFileName().trim().length() != 0) {
						// 중복된 파일명이 서버에 있는 경우 새로운 이름으로 변경
						String systemFilename = FileUtils.getNewFileName(saveDirectory, part.getSubmittedFileName());
						Map<String, String> fileMap = new HashMap<>();
						fileMap.put("contentype", part.getContentType());// 컨텐츠 타입
						fileMap.put("filesize", String.valueOf(part.getSize()));// 파일 크기(바이트)
						fileMap.put("submittedfilename", part.getSubmittedFileName());// 원본 파일명
						fileMap.put("systemfilename", systemFilename);// 서버에 저장된 실제 파일명
						files.add(fileMap);
						// 파일 업로드
						part.write(saveDirectory + File.separator + systemFilename);
						FilesDto filesDto = new FilesDto(systemFilename, username);
						list.add(filesDto);
					}
				}
			}
		int no = dao.insert(dto);
		for(FilesDto filesDto:list) {
			FilesDao filesDao = new FilesDao(getServletContext());
			filesDto.setNo(no);
			filesDao.insert(filesDto);
		}
		resp.sendRedirect(req.getContextPath()+"/board/ListPage.do");
		}catch(Exception e) {
			System.out.println(e);
			resp.setContentType("text/html; charset=UTF-8");
			PrintWriter out= resp.getWriter();
			out.println("<script>");
			out.println("alert('파일용량 초과(파일당 5mb)')");
			out.println("history.back()");
			out.println("</script>");
		}
	}

}
