package servlet;

import model.ocr.Tesseract;
import org.bytedeco.javacpp.lept;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

public class ImageServlet extends HttpServlet {

    private static final String IMAGE_KEY = "image";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        final Part part = request.getPart(IMAGE_KEY);
        final lept.PIX pix = readPixFromPart(part);

        final Tesseract tesseract = new Tesseract("rus");

        final String ocr = tesseract.ocr(pix);
        System.out.println(ocr);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private lept.PIX readPixFromPart(Part part) {
        final int size = (int) part.getSize();
        final InputStream inputStream;
        try {
            inputStream = part.getInputStream();
            byte[] bytes = new byte[size];
            inputStream.read(bytes, 0, size);
            return lept.pixReadMem(bytes, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
