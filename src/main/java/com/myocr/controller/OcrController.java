package com.myocr.controller;

import com.myocr.controller.json.OcrReceiptResponse;
import com.myocr.model.align.ReceiptItemMatches;
import com.myocr.model.ocr.OcrUtil;
import com.myocr.model.ocr.ParsedPrice;
import com.myocr.model.ocr.PriceParser;
import com.myocr.model.ocr.Tesseract;
import com.myocr.repository.ReceiptItemRepository;
import com.myocr.service.ReceiptItemService;
import com.myocr.util.TimeUtil;
import org.bytedeco.javacpp.lept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ocr")
public class OcrController {
    private final static Logger log = LoggerFactory.getLogger(OcrController.class);

    @Autowired
    private ReceiptItemRepository receiptItemRepository;

    @PostMapping("/{city}/{shop}")
    public OcrReceiptResponse ocrImage(
            @RequestParam MultipartFile receiptItemsImage,
            @RequestParam MultipartFile pricesImage,
            @PathVariable String city, @PathVariable String shop) throws IOException {

        log.info(receiptItemsImage.getOriginalFilename());
        log.info(pricesImage.getOriginalFilename());

        save(receiptItemsImage);
        save(pricesImage);

        final List<String> ocrReceiptItems = ocrReceiptItemsImage(receiptItemsImage);
        log.info(Arrays.toString(ocrReceiptItems.toArray()));

        final ReceiptItemService service = new ReceiptItemService(receiptItemRepository);
        final List<ReceiptItemMatches> receiptItemMatches = service.findReceipt(city, shop, ocrReceiptItems);

        final List<String> ocrPrices = ocrPricesImage(pricesImage);
        log.info(Arrays.toString(ocrPrices.toArray()));

        List<ParsedPrice> parsedPrices = PriceParser.parse(ocrPrices);

        return new OcrReceiptResponse(receiptItemMatches, parsedPrices);
    }

    private List<String> ocrReceiptItemsImage(MultipartFile image) throws IOException {
        final lept.PIX pix = lept.pixReadMem(image.getBytes(), image.getSize());
        final Tesseract tesseract = new Tesseract("rus");

        final String ocrText = tesseract.ocr(pix);
        pix.deallocate();
        tesseract.release();

        return OcrUtil.parse(ocrText);
    }

    private List<String> ocrPricesImage(MultipartFile image) throws IOException {
        final lept.PIX pix = lept.pixReadMem(image.getBytes(), image.getSize());
        final Tesseract tesseract = new Tesseract("rus");
        tesseract.setCharWhitelist("0123456789.");

        final String ocrText = tesseract.ocr(pix);
        pix.deallocate();
        tesseract.release();

        return OcrUtil.parse(ocrText);
    }

    private File save(MultipartFile file) throws IOException {
        final Date serverTime = new Date();
        final String timePrefix = TimeUtil.getFileTimePrefix(serverTime);
        final String filePath = "receivedImages/" + timePrefix + file.getOriginalFilename();

        final File dest = new File(filePath);
        dest.getParentFile().mkdirs();
        dest.createNewFile();
        final FileOutputStream fos = new FileOutputStream(dest);
        fos.write(file.getBytes());
        fos.close();
        return dest;
    }
}
