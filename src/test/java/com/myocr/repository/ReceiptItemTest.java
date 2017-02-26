package com.myocr.repository;

import com.myocr.Application;
import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.ReceiptItem;
import com.myocr.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class ReceiptItemTest {

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
    public void findByNameAndCityShopReceiptItemsCityShopCityNameAndCityShopReceiptItemsCityShopShopName() throws Exception {
        final City spb = cityRepository.save(new City("Spb"));
        final Shop auchan = shopRepository.save(new Shop("Auchan"));
        CityShop spbAuchan = CityShop.link(spb, auchan);
        spbAuchan = cityShopRepository.save(spbAuchan);
        final ReceiptItem pizza = receiptItemRepository.save(new ReceiptItem("Pizza"));
        final CityShopReceiptItem spbAuchanPizza = new CityShopReceiptItem(pizza, spbAuchan);
        pizza.getCityShopReceiptItems().add(spbAuchanPizza);
        cityShopReceiptItemRepository.save(spbAuchanPizza);

        assertThat(receiptItemRepository.count(), is(1L));

        final ReceiptItem item = receiptItemRepository
                .findByNameAndCityShopReceiptItemsCityShopCityNameAndCityShopReceiptItemsCityShopShopName(
                        "Pizza", "Spb", "Auchan");

        assertThat(item.getName(), is("Pizza"));

        final List<CityShopReceiptItem> cityShopReceiptItems = item.getCityShopReceiptItems();
        assertThat(cityShopReceiptItems, hasSize(1));

        final CityShopReceiptItem cityShopReceiptItem = cityShopReceiptItems.get(0);
        assertThat(cityShopReceiptItem.getCityShop().getCity().getName(), is("Spb"));
        assertThat(cityShopReceiptItem.getCityShop().getShop().getName(), is("Auchan"));
    }
}
