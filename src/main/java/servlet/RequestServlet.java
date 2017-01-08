package servlet;

import com.google.gson.Gson;
import model.FindAllRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class RequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FindAllRequest findAllRequest = new FindAllRequest();
        try {
            BufferedReader reader = req.getReader();
            Gson gson = new Gson();
            findAllRequest = gson.fromJson(reader, FindAllRequest.class);
        } catch (Exception e) {
        }
        System.out.println(Arrays.toString(findAllRequest.products.toArray()));

        /*if (message == null || message.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
        }*/

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println("{\n\"key\": \"" + "okPasha" + "\"\n}");
    }
}
