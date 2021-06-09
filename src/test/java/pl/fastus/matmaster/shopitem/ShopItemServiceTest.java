package pl.fastus.matmaster.shopitem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.fastus.matmaster.shopitem.dto.ShopItemResponse;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShopItemServiceTest {

    @Mock
    ShopItemRepository repository;

    @Mock
    ShopItemMapper mapper;

    @InjectMocks
    ShopItemService service;

    List<ShopItem> shopItems;

    @BeforeEach
    void setUp() {

        shopItems = getShopItemList();
    }

    @Test
    void getAllShopItems() {
        ShopItemResponse shopItemResponseOne = new ShopItemResponse().setId(1L).setName("Item1")
                .setSubTitle("Subtitle1").setDescription("Description1")
                .setPrice(BigDecimal.ONE);

        ShopItemResponse shopItemResponseTwo = new ShopItemResponse().setId(2L).setName("Item2")
                .setSubTitle("Subtitle2").setDescription("Description2")
                .setPrice(BigDecimal.TEN);

        given(repository.findAll()).willReturn(shopItems);
        given(mapper.toShopItemResponse(any())).willReturn(shopItemResponseOne);
        given(mapper.toShopItemResponse(any())).willReturn(shopItemResponseTwo);

        List<ShopItemResponse> responses = service.getAllShopItems();

        assertEquals(2, responses.size());
        assertEquals(2, responses.get(1).getId());

        verify(repository, times(1)).findAll();
        verify(mapper,times(2)).toShopItemResponse(any());
    }

    private List<ShopItem> getShopItemList() {
        return List.of(
                ShopItem.builder().build(),
                ShopItem.builder().build()
        );
    }
}
