package pl.fastus.matmaster.shopitem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.fastus.matmaster.blogpost.dto.BlogPostResponse;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.shopitem.dto.ShopItemRequest;
import pl.fastus.matmaster.shopitem.dto.ShopItemResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopItemServiceTest {

    public static final long ID_ONE = 1L;
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

        ShopItemResponse foundItem = service.getShopItemById(ID_ONE);

        assertEquals(ID_ONE, foundItem.getId());

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

    @Test
    void createShopItem() {
        ShopItemRequest shopItemRequest = new ShopItemRequest();
        ShopItem toSave = new ShopItem();
        ShopItem savedShopItem = ShopItem.builder().id(ID_ONE).build();

        given(mapper.toShopItem(any())).willReturn(toSave);
        given(repository.save(any(ShopItem.class))).willReturn(savedShopItem);

        Long savedItemId = service.createShopItem(shopItemRequest);

        assertEquals(1, savedItemId);
    }

    @Test
    void updateShopItem() {
        ShopItemRequest update = new ShopItemRequest()
                .setName("Item name")
                .setPrice(BigDecimal.valueOf(125.20));

        ShopItemResponse response = new ShopItemResponse()
                .setName("Item name")
                .setPrice(BigDecimal.valueOf(125.20));

        given(repository.findById(any())).willReturn(Optional.of(shopItems.get(0)));
        given(mapper.toShopItemResponse(any(ShopItem.class))).willReturn(response);

        ShopItemResponse updatedItemResponse = service.updateShopItem(ID_ONE, update);

        assertAll(
                ()->assertEquals("Item name", updatedItemResponse.getName()),
                ()->assertEquals(BigDecimal.valueOf(125.20), updatedItemResponse.getPrice()),
                ()->assertNull(updatedItemResponse.getSubTitle()),
                ()->assertNull(updatedItemResponse.getDescription())
        );
    }

    @Test
    void deactivateShopItem(){
        ShopItem shopItem = shopItems.get(0);
        given(repository.findById(any())).willReturn(Optional.of(shopItem));

        Long shopItemId = service.deactivate(ID_ONE);

        assertEquals(ID_ONE, shopItemId);
        assertEquals(Status.INACTIVE, shopItem.getStatus());
    }

    @Test
    void deactivateShopItemThrowException(){
        given(repository.findById(any())).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, ()->service.deactivate(5L));
    }

    private List<ShopItem> getShopItemList() {
        return List.of(
                ShopItem.builder().id(1L).name("Item1").subTitle("Subtitle1")
                        .description("Description1").price(BigDecimal.ONE).build(),
                ShopItem.builder().build()
        );
    }
}
