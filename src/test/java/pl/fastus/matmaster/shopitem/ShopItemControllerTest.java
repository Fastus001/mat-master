package pl.fastus.matmaster.shopitem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.fastus.matmaster.shopitem.dto.ShopItemResponse;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ShopItemControllerTest {

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
        BDDMockito.given(service.getAllShopItems()).willReturn(shopItemResponses);

        mockMvc.perform(get("/api/v1/shop-item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Kurs Matematyki")))
                .andExpect(jsonPath("$[1].subTitle", is("Klasa matura")))
                .andExpect(jsonPath("$[1].description", is("Przygotowanie do matury z matematyki")))
                .andExpect(jsonPath("$[1].price", is(499.25)));
    }

    private List<ShopItemResponse> getShopItemResponses() {
        return List.of(
                new ShopItemResponse().setId(1L)
                        .setName("Kurs Matematyki")
                        .setSubTitle("Klasa 8")
                        .setDescription("Przygotowanie do egzaminu 8 klasisty")
                        .setPrice(BigDecimal.valueOf(225.25)),
                new ShopItemResponse().setId(2L)
                        .setName("Kurs Matematyki")
                        .setSubTitle("Klasa matura")
                        .setDescription("Przygotowanie do matury z matematyki")
                        .setPrice(BigDecimal.valueOf(499.25))
        );
    }
}
