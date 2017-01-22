import db.DbService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.FindAllServlet;
import servlet.ImageServlet;

import javax.servlet.MultipartConfigElement;

public class Main {
    public static void main(String[] args) throws Exception {
        final DbService dbService = new DbService();

        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        final String requestedUrl = "/findAll";
        final FindAllServlet findAllServlet = new FindAllServlet();
        context.addServlet(new ServletHolder(findAllServlet), requestedUrl);

        final String imageUrl = "/image";
        final ImageServlet imageServlet = new ImageServlet();
        final ServletHolder servletHolder = new ServletHolder(imageServlet);
        servletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement("data/tmp"));
        context.addServlet(servletHolder, imageUrl);

        final Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
