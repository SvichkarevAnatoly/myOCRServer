package com.myocr.controller;

import com.myocr.model.ocr.Tesseract;
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

@RestController
@RequestMapping("/ocr")
public class OcrController {
    final static Logger log = LoggerFactory.getLogger(OcrController.class);

    @PostMapping("/image")
    public String handleFileUpload(@RequestParam("file") MultipartFile image) throws IOException {
        log.info(image.getOriginalFilename());

        final lept.PIX pix = lept.pixReadMem(image.getBytes(), image.getSize());

        final Tesseract tesseract = new Tesseract("rus");
        final String ocrText = tesseract.ocr(pix);
        pix.deallocate();

        log.info(ocrText);

        return ocrText;
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
