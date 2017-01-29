package servlet;

import com.google.gson.Gson;
import connection.InsertRequest;
import db.DbService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class InsertServlet extends HttpServlet {
    private final DbService dbService;

    public InsertServlet(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InsertRequest insertRequest = null;
        Gson gson = new Gson();
        try {
            BufferedReader reader = req.getReader();
            insertRequest = gson.fromJson(reader, InsertRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(insertRequest.productDataSets.toArray()));

        dbService.insertProductDataSet(insertRequest.productDataSets);

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
