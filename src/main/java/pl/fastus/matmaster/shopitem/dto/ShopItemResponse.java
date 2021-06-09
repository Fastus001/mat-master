package pl.fastus.matmaster.shopitem.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Accessors(chain = true)
@Data
public class ShopItemResponse {

    private Long id;
    private String name;
    private String subTitle;
    private String description;
    private BigDecimal price;
}
