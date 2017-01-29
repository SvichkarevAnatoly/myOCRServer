package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class MirrorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        final StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(sb);
        System.out.println(sb);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String message = request.getParameter("key");
        if (message == null || message.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(message);
    }
}
