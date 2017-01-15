package servlet;

import com.google.gson.Gson;
import connection.OcrResponse;
import model.ocr.Tesseract;
import org.bytedeco.javacpp.lept;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import static org.bytedeco.javacpp.lept.IFF_PNG;

public class ImageServlet extends HttpServlet {

    private static final String IMAGE_KEY = "imageUri";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        final Part part = request.getPart(IMAGE_KEY);
        final lept.PIX pix = readPixFromPart(part);

        lept.pixWrite(String.format("receivedImages/out%s.png", Calendar.getInstance().getTime().toString()), pix, IFF_PNG);

        final Tesseract tesseract = new Tesseract("rus");
        /*final String russianAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
                "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
                "0123456789" +
                "/.%\"";*/
        // tesseract.setCharWhitelist(russianAlphabet);

        final String ocrText = tesseract.ocr(pix);
        pix.deallocate();
        System.out.println(ocrText);

        final OcrResponse ocrResponse = new OcrResponse(ocrText);

        Gson gson = new Gson();
        final String json = gson.toJson(ocrResponse);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getWriter().write(json);
    }

    private lept.PIX readPixFromPart(Part part) {
        final InputStream inputStream;
        try {
            inputStream = part.getInputStream();
            final int size = (int) part.getSize();
            byte[] bytes = new byte[size];
            inputStream.read(bytes, 0, size);
            return lept.pixReadMem(bytes, size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
