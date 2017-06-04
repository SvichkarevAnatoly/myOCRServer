package com.myocr.repository;

import com.myocr.AbstractSpringTest;
import com.myocr.entity.City;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.ReceiptItem;
import com.myocr.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.myocr.entity.Cities.Spb;
import static com.myocr.entity.ReceiptItems.Pizza;
import static com.myocr.entity.Shops.Auchan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class ReceiptItemRepositoryTest extends AbstractSpringTest {

    @Override
    protected void setUp() throws Exception {
        generateReceiptItem(Pizza, Spb, Auchan);
    }

    @Test
    public void findByNameAndCityAndShop() throws Exception {
        final ReceiptItem item = receiptItemRepository
                .findByNameAndCityShopReceiptItemsCityShopCityNameAndCityShopReceiptItemsCityShopShopName(
                        Pizza.name(), Spb.name(), Auchan.name());

        assertThat(item.getName(), is(Pizza.name()));

        final List<CityShopReceiptItem> cityShopReceiptItems = item.getCityShopReceiptItems();
        assertThat(cityShopReceiptItems, hasSize(1));

        final CityShopReceiptItem cityShopReceiptItem = cityShopReceiptItems.get(0);
        assertThat(cityShopReceiptItem.getCityShop().getCity().getName(), is(Spb.name()));
        assertThat(cityShopReceiptItem.getCityShop().getShop().getName(), is(Auchan.name()));
    }

    @Test
    public void findByCityAndShop() throws Exception {
        final City spb = cityRepository.findByName(Spb.name());
        final Shop auchan = shopRepository.findByName(Auchan.name());
        final List<ReceiptItem> items = receiptItemRepository
                .findByCityShopReceiptItemsCityShopCityIdAndCityShopReceiptItemsCityShopShopId(
                        spb.getId(), auchan.getId());
        assertThat(items, hasSize(1));

        final ReceiptItem item = items.get(0);
        assertThat(item.getName(), is(Pizza.name()));

        final List<CityShopReceiptItem> cityShopReceiptItems = item.getCityShopReceiptItems();
        assertThat(cityShopReceiptItems, hasSize(1));

        final CityShopReceiptItem cityShopReceiptItem = cityShopReceiptItems.get(0);
        assertThat(cityShopReceiptItem.getCityShop().getCity().getName(), is(Spb.name()));
        assertThat(cityShopReceiptItem.getCityShop().getShop().getName(), is(Auchan.name()));
    }
}
