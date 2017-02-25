package com.myocr.controller;

import com.myocr.entity.CityShop;
import com.myocr.entity.Price;
import com.myocr.entity.ReceiptItem;
import com.myocr.repository.CityShopRepository;
import com.myocr.repository.PriceRepository;
import com.myocr.repository.ReceiptItemRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private final PriceRepository priceRepository;
    private final CityShopRepository cityShopRepository;
    private final ReceiptItemRepository receiptItemRepository;

    public PriceController(PriceRepository priceRepository,
                           CityShopRepository cityShopRepository,
                           ReceiptItemRepository receiptItemRepository) {
        this.priceRepository = priceRepository;
        this.cityShopRepository = cityShopRepository;
        this.receiptItemRepository = receiptItemRepository;
    }

    @RequestMapping(value = "addNew", method = RequestMethod.POST)
    public Price save(@RequestBody RequestPriceBody request) {
        final CityShop cityShop = cityShopRepository.findByCityNameAndShopName(
                request.getCityName(), request.getShopName());

        final ReceiptItem receiptItem = receiptItemRepository.findByName(request.getReceiptItemName());

        final Price price = new Price();
        price.setCityShop(cityShop);
        price.setValue(request.getPriceValue());
        price.setReceiptItem(receiptItem);

        return priceRepository.save(price);
    }

    private static class RequestPriceBody {
        private String cityName;
        private String shopName;

        private String receiptItemName;
        private String priceValue;

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getReceiptItemName() {
            return receiptItemName;
        }

        public void setReceiptItemName(String receiptItemName) {
            this.receiptItemName = receiptItemName;
        }

        public String getPriceValue() {
            return priceValue;
        }

        public void setPriceValue(String priceValue) {
            this.priceValue = priceValue;
        }
    }
}
