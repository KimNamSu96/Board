package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Down.do")
public class DownServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String filepath = req.getParameter("filepath");
		String saveDirectory=req.getServletContext().getRealPath("/upload");
		File file = new File(saveDirectory+File.separator+filepath);
		resp.setContentType("application/octet-stream");
		resp.setContentLength((int) file.length());
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + filepath + "\"");
		
		try (FileInputStream fis = new FileInputStream(file); OutputStream os = resp.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
	}

}
