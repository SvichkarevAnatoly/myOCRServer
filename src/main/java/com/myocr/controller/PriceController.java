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
import com.myocr.util.TextFieldUtil;
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
            if (!TextFieldUtil.isValid(requestItem.getName()) || (requestItem.getPrice() <= 0)) {
                continue;
            }
            final CityShopReceiptItem item = getCityShopReceiptItem(
                    requestItem.getName(), request.getCityId(), request.getShopId());
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

    private CityShopReceiptItem getCityShopReceiptItem(String receiptItem, long cityId, long shopId) {
        CityShopReceiptItem item = cityShopReceiptItemRepository
                .findByReceiptItemNameAndCityShopCityIdAndCityShopShopId(receiptItem, cityId, shopId);
        if (item == null) {
            final CityShop dbCityShop = cityShopRepository.findByCityIdAndShopId(cityId, shopId);
            if (dbCityShop == null) {
                throw new IllegalArgumentException("Shop in city must exist!");
            }

            final String preparedReceiptItem = TextFieldUtil.prepare(receiptItem);
            ReceiptItem dbItem = receiptItemRepository.findByName(preparedReceiptItem);
            if (dbItem == null) {
                dbItem = receiptItemRepository.save(new ReceiptItem(preparedReceiptItem));
            }

            item = cityShopReceiptItemRepository.save(new CityShopReceiptItem(dbItem, dbCityShop));
        }

        return item;
    }
}
