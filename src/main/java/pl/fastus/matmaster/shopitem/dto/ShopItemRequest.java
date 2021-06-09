package pl.fastus.matmaster.shopitem.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopItemRequest {

    private String name;
    private String subTitle;
    private String description;
    private BigDecimal price;
}
