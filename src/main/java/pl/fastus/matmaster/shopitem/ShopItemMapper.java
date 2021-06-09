package pl.fastus.matmaster.shopitem;

import org.mapstruct.Mapper;
import pl.fastus.matmaster.shopitem.dto.ShopItemRequest;

@Mapper(componentModel = "spring")
public interface ShopItemMapper {

    ShopItem toShopItem(ShopItemRequest request);
}
