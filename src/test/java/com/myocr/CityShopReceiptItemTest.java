package com.myocr;

import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.ReceiptItem;
import com.myocr.entity.Shop;
import com.myocr.repository.CityRepository;
import com.myocr.repository.CityShopReceiptItemRepository;
import com.myocr.repository.CityShopRepository;
import com.myocr.repository.ReceiptItemRepository;
import com.myocr.repository.ShopRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class CityShopReceiptItemTest {

    @Autowired
    private CityShopReceiptItemRepository cityShopReceiptItemRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CityShopRepository cityShopRepository;

    @Autowired
    private ReceiptItemRepository receiptItemRepository;

    @Test
    public void findByReceiptItemNameAndCityShopCityNameAndCityShopShopName() throws Exception {
        final City spb = cityRepository.save(new City("Spb"));
        final Shop auchan = shopRepository.save(new Shop("Auchan"));
        CityShop spbAuchan = CityShop.link(spb, auchan);
        spbAuchan = cityShopRepository.save(spbAuchan);
        final ReceiptItem pizza = receiptItemRepository.save(new ReceiptItem("Pizza"));
        cityShopReceiptItemRepository.save(new CityShopReceiptItem(pizza, spbAuchan));

        assertThat(cityShopReceiptItemRepository.count(), is(1L));

        final CityShopReceiptItem item = cityShopReceiptItemRepository
                .findByReceiptItemNameAndCityShopCityNameAndCityShopShopName(
                        "Pizza", "Spb", "Auchan");

        assertThat(item.getReceiptItem().getName(), is("Pizza"));
        assertThat(item.getCityShop().getCity().getName(), is("Spb"));
        assertThat(item.getCityShop().getShop().getName(), is("Auchan"));
    }
}
