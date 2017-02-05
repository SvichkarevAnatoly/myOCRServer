package servlet;

import align.DataBaseFinder;
import com.google.gson.Gson;
import db.DbService;
import servlet.connection.FindAllRequest;
import servlet.connection.FindAllResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FindServlet extends HttpServlet {
    private final DbService dbService;

    public FindServlet(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FindAllRequest findAllRequest = new FindAllRequest();
        Gson gson = new Gson();
        try {
            BufferedReader reader = req.getReader();
            findAllRequest = gson.fromJson(reader, FindAllRequest.class);
        } catch (Exception e) {
        }
        System.out.println(Arrays.toString(findAllRequest.products.toArray()));

        final List<String> allProducts = dbService.getAllProducts();
        final DataBaseFinder finder = new DataBaseFinder(allProducts);
        final List<String> matches = finder.findAll(findAllRequest.products);
        System.out.println(Arrays.toString(matches.toArray()));

        final FindAllResponse findAllResponse = new FindAllResponse(matches);
        final String json = gson.toJson(findAllResponse);

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);

        resp.getWriter().write(json);
    }
}
