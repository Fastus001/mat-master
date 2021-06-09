package pl.fastus.matmaster.shopitem;

import org.junit.jupiter.api.Test;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.shopitem.dto.ShopItemRequest;
import pl.fastus.matmaster.shopitem.dto.ShopItemResponse;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ShopItemMapperTest {

    public static final String ITEM = "Item";
    public static final String SUBTITLE = "Subtitle";
    public static final String DESCRIPTION = "Description";
    ShopItemMapper mapper = new ShopItemMapperImpl();

    @Test
    void toShopItem() {
        ShopItemRequest shopItemRequest = new ShopItemRequest()
                .setName(ITEM)
                .setSubTitle(SUBTITLE)
                .setDescription(DESCRIPTION)
                .setPrice(BigDecimal.TEN);

        ShopItem shopItem = mapper.toShopItem(shopItemRequest);

        assertAll(() -> assertEquals(ITEM, shopItem.getName()),
                () -> assertEquals(SUBTITLE, shopItem.getSubTitle()),
                () -> assertEquals(DESCRIPTION, shopItem.getDescription()),
                () -> assertEquals(BigDecimal.TEN, shopItem.getPrice()));
    }

    @Test
    void toShopItemResponse() {
        ShopItem shopItem = ShopItem.builder().id(1L)
                .name(ITEM)
                .subTitle(SUBTITLE)
                .description(DESCRIPTION)
                .price(BigDecimal.TEN)
                .status(Status.ACTIVE).build();

        ShopItemResponse shopItemResponse = mapper.toShopItemResponse(shopItem);

        assertAll(
                () -> assertEquals(1, shopItemResponse.getId()),
                () -> assertEquals(ITEM, shopItemResponse.getName()),
                () -> assertEquals(SUBTITLE, shopItemResponse.getSubTitle()),
                () -> assertEquals(DESCRIPTION, shopItemResponse.getDescription()),
                () -> assertEquals(BigDecimal.TEN, shopItemResponse.getPrice()));
    }
}
