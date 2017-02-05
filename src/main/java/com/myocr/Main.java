package com.myocr;

import com.myocr.db.DbService;
import com.myocr.servlet.FindServlet;
import com.myocr.servlet.ImageServlet;
import com.myocr.servlet.InsertServlet;
import com.myocr.servlet.MirrorServlet;
import com.myocr.util.TessdataUtil;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.MultipartConfigElement;

public class Main {
    public static void main(String[] args) throws Exception {
        TessdataUtil.extractTessdata();
        final DbService dbService = new DbService();

        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        // For debugging
        final String mirrorUrl = "/mirror";
        final MirrorServlet mirrorServlet = new MirrorServlet();
        context.addServlet(new ServletHolder(mirrorServlet), mirrorUrl);

        final String findUrl = "/find";
        final FindServlet findServlet = new FindServlet(dbService);
        context.addServlet(new ServletHolder(findServlet), findUrl);

        final String imageUrl = "/image";
        final ImageServlet imageServlet = new ImageServlet();
        final ServletHolder servletHolder = new ServletHolder(imageServlet);
        servletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement("data/tmp"));
        context.addServlet(servletHolder, imageUrl);

        final String insertUrl = "/add";
        final InsertServlet insertServlet = new InsertServlet(dbService);
        context.addServlet(new ServletHolder(insertServlet), insertUrl);

        final Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
