package com.myocr.controller;

import com.myocr.model.align.ReceiptItemMatches;
import com.myocr.model.ocr.OcrUtil;
import com.myocr.model.ocr.Tesseract;
import com.myocr.repository.ReceiptItemRepository;
import com.myocr.service.ReceiptItemService;
import org.bytedeco.javacpp.lept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ocr")
public class OcrController {
    final static Logger log = LoggerFactory.getLogger(OcrController.class);

    private final ReceiptItemRepository receiptItemRepository;

    public OcrController(ReceiptItemRepository receiptItemRepository) {
        this.receiptItemRepository = receiptItemRepository;
    }

    @PostMapping("/image")
    public List<ReceiptItemMatches> ocrImage(@RequestParam("file") MultipartFile image,
                                             @RequestParam String city, @RequestParam String shop) throws IOException {
        log.info(image.getOriginalFilename());

        final lept.PIX pix = lept.pixReadMem(image.getBytes(), image.getSize());
        final Tesseract tesseract = new Tesseract("rus");

        final String ocrText = tesseract.ocr(pix);
        pix.deallocate();
        tesseract.release();

        log.info(ocrText);

        final List<String> ocrReceiptItems = OcrUtil.parse(ocrText);

        final ReceiptItemService service = new ReceiptItemService(receiptItemRepository);
        return service.findReceipt(city, shop, ocrReceiptItems);
    }

    private File save(MultipartFile file) throws IOException {
        final File dest = new File(file.getOriginalFilename());
        dest.createNewFile();
        FileOutputStream fos = new FileOutputStream(dest);
        fos.write(file.getBytes());
        fos.close();
        return dest;
    }
}
