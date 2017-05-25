package com.myocr.repository;

import com.myocr.AbstractSpringTest;
import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.ReceiptItem;
import com.myocr.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class CityShopReceiptItemRepositoryTest extends AbstractSpringTest {

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
