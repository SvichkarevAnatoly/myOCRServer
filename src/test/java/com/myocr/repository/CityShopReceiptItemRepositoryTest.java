package com.myocr.repository;

import com.myocr.AbstractSpringTest;
import com.myocr.entity.CityShopReceiptItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static com.myocr.entity.Cities.Spb;
import static com.myocr.entity.ReceiptItems.Pizza;
import static com.myocr.entity.Shops.Auchan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class CityShopReceiptItemRepositoryTest extends AbstractSpringTest {

    @Test
    public void findByReceiptItemAndCityAndShop() throws Exception {
        generateReceiptItem(Pizza, Spb, Auchan);
        assertThat(cityShopReceiptItemRepository.count(), is(1L));

        final CityShopReceiptItem item = cityShopReceiptItemRepository
                .findByReceiptItemNameAndCityShopCityNameAndCityShopShopName(
                        Pizza.name(), Spb.name(), Auchan.name());

        assertThat(item.getReceiptItem().getName(), is(Pizza.name()));
        assertThat(item.getCityShop().getCity().getName(), is(Spb.name()));
        assertThat(item.getCityShop().getShop().getName(), is(Auchan.name()));
    }
}
