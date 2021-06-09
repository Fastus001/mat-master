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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopItemServiceTest {

    @Mock
    ShopItemRepository repository;

    @Mock
    ShopItemMapper mapper;

    @InjectMocks
    ShopItemService service;

    List<ShopItem> shopItems;

    ShopItemResponse shopItemResponseOne;
    ShopItemResponse shopItemResponseTwo;

    @BeforeEach
    void setUp() {

        shopItems = getShopItemList();


        shopItemResponseOne = new ShopItemResponse().setId(1L).setName("Item1")
                .setSubTitle("Subtitle1").setDescription("Description1")
                .setPrice(BigDecimal.ONE);

        shopItemResponseTwo = new ShopItemResponse().setId(2L).setName("Item2")
                .setSubTitle("Subtitle2").setDescription("Description2")
                .setPrice(BigDecimal.TEN);
    }

    @Test
    void getAllShopItems() {


        given(repository.findAll()).willReturn(shopItems);
        given(mapper.toShopItemResponse(any())).willReturn(shopItemResponseOne);
        given(mapper.toShopItemResponse(any())).willReturn(shopItemResponseTwo);

        List<ShopItemResponse> responses = service.getAllShopItems();

        assertEquals(2, responses.size());
        assertEquals(2, responses.get(1).getId());

        verify(repository, times(1)).findAll();
        verify(mapper,times(2)).toShopItemResponse(any());
    }

    @Test
    void getShopItemById() {
        given(repository.findById(any())).willReturn(Optional.of(shopItems.get(0)));
        given(mapper.toShopItemResponse(any())).willReturn(shopItemResponseOne);

        ShopItemResponse foundItem = service.getShopItemById(1L);

        assertEquals(1, foundItem.getId());

        verify(repository, times(1)).findById(any());
        verify(mapper,times(1)).toShopItemResponse(any());
    }

    @Test
    void getShopItemByIdShouldThrownException() {
        given(repository.findById(any())).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, ()->service.getShopItemById(3L));

        verify(repository, times(1)).findById(any());
        verifyNoInteractions(mapper);
    }

    private List<ShopItem> getShopItemList() {
        return List.of(
                ShopItem.builder().id(1L).name("Item1").subTitle("Subtitle1")
                        .description("Description1").price(BigDecimal.ONE).build(),
                ShopItem.builder().build()
        );
    }
}
