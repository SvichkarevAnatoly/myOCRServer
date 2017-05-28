package com.myocr.controller;

import com.myocr.controller.json.SavePriceRequest;
import com.myocr.entity.CityShop;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.Price;
import com.myocr.entity.ReceiptItem;
import com.myocr.repository.CityShopReceiptItemRepository;
import com.myocr.repository.CityShopRepository;
import com.myocr.repository.PriceRepository;
import com.myocr.repository.ReceiptItemRepository;
import com.myocr.util.ReceiptItemUtil;
import com.myocr.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/prices")
public class PriceController {
    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private ReceiptItemRepository receiptItemRepository;

    @Autowired
    private CityShopRepository cityShopRepository;

    @Autowired
    private CityShopReceiptItemRepository cityShopReceiptItemRepository;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public int save(@RequestBody SavePriceRequest request) throws ParseException {
        Date time = getRequestTime(request);

        final List<Price> prices = new ArrayList<>();
        for (SavePriceRequest.ReceiptPriceItem requestItem : request.getItems()) {
            if (!ReceiptItemUtil.isValid(requestItem.getName()) || (requestItem.getPrice() <= 0)) {
                continue;
            }
            final CityShopReceiptItem item = getCityShopReceiptItem(
                    requestItem.getName(), request.getCityName(), request.getShopName());
            final Price price = new Price(item, requestItem.getPrice(), time);
            prices.add(price);
        }

        priceRepository.save(prices);
        return prices.size();
    }

    private Date getRequestTime(SavePriceRequest request) throws ParseException {
        Date time;
        String requestTime = request.getTime();
        if (StringUtils.isEmpty(requestTime)) {
            time = new Date();
        } else {
            time = TimeUtil.parse(requestTime);
        }
        return time;
    }

    private CityShopReceiptItem getCityShopReceiptItem(String receiptItem, String city, String shop) {
        CityShopReceiptItem item = cityShopReceiptItemRepository
                .findByReceiptItemNameAndCityShopCityNameAndCityShopShopName(receiptItem, city, shop);
        if (item == null) {
            final CityShop dbCityShop = cityShopRepository.findByCityNameAndShopName(city, shop);
            if (dbCityShop == null) {
                throw new IllegalArgumentException("Shop in city must exist!");
            }

            final String preparedReceiptItem = ReceiptItemUtil.prepareToSave(receiptItem);
            ReceiptItem dbItem = receiptItemRepository.findByName(preparedReceiptItem);
            if (dbItem == null) {
                dbItem = receiptItemRepository.save(new ReceiptItem(preparedReceiptItem));
            }

            item = cityShopReceiptItemRepository.save(new CityShopReceiptItem(dbItem, dbCityShop));
        }

        return item;
    }
}
