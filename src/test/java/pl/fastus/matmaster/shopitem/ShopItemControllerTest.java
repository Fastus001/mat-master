package pl.fastus.matmaster.shopitem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.fastus.matmaster.shopitem.dto.ShopItemRequest;
import pl.fastus.matmaster.shopitem.dto.ShopItemResponse;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ShopItemControllerTest {

    public static final String API_V_1_SHOP_ITEM = "/api/v1/shop-item";
    public static final long ID_TWO = 2L;
    public static final String NAME = "Kurs Matematyki";
    public static final String SUBTITLE = "Klasa matura";
    public static final String DESCRIPTION = "Przygotowanie do matury z matematyki";
    public static final double VALUE = 499.25;
    @Mock
    ShopItemService service;

    @InjectMocks
    ShopItemController controller;

    MockMvc mockMvc;

    List<ShopItemResponse> shopItemResponses;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        shopItemResponses = getShopItemResponses();
    }

    @Test
    void getAllShopItems() throws Exception {
        given(service.getAllShopItems()).willReturn(shopItemResponses);

        mockMvc.perform(get(API_V_1_SHOP_ITEM))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is(NAME)))
                .andExpect(jsonPath("$[1].subTitle", is(SUBTITLE)))
                .andExpect(jsonPath("$[1].description", is(DESCRIPTION)))
                .andExpect(jsonPath("$[1].price", is(VALUE)));
    }

    @Test
    void getShopItemById() throws Exception {
        given(service.getShopItemById(any())).willReturn(shopItemResponses.get(1));

        mockMvc.perform(get(API_V_1_SHOP_ITEM + "/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.subTitle", is(SUBTITLE)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)))
                .andExpect(jsonPath("$.price", is(VALUE)));

    }
    
    @Test
    void createShopItem() throws Exception {
        ShopItemRequest requestToSave = new ShopItemRequest().setName(NAME)
                .setSubTitle(SUBTITLE)
                .setDescription(DESCRIPTION)
                .setPrice(BigDecimal.valueOf(VALUE));

        given(service.createShopItem(requestToSave)).willReturn(2L);

        String content = mockMvc.perform(post(API_V_1_SHOP_ITEM)
                .content(asJsonString(requestToSave))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("2", content);
    }

    @Test
    void updateShopItem() throws Exception {
        ShopItemRequest request = new ShopItemRequest().setName(NAME)
                .setSubTitle(SUBTITLE)
                .setDescription(DESCRIPTION)
                .setPrice(BigDecimal.valueOf(VALUE));

        given(service.updateShopItem(any(), any(ShopItemRequest.class)))
                .willReturn(shopItemResponses.get(1));

        mockMvc.perform(put(API_V_1_SHOP_ITEM + "/2")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.subTitle", is(SUBTITLE)))
                .andExpect(jsonPath("$.description", is(DESCRIPTION)))
                .andExpect(jsonPath("$.price", is(VALUE)));
    }

    private List<ShopItemResponse> getShopItemResponses() {
        return List.of(
                new ShopItemResponse().setId(1L)
                        .setName("Kurs Matematyki")
                        .setSubTitle("Klasa 8")
                        .setDescription("Przygotowanie do egzaminu 8 klasisty")
                        .setPrice(BigDecimal.valueOf(225.25)),
                new ShopItemResponse().setId(ID_TWO)
                        .setName(NAME)
                        .setSubTitle(SUBTITLE)
                        .setDescription(DESCRIPTION)
                        .setPrice(BigDecimal.valueOf(VALUE))
        );
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
