package pl.fastus.matmaster.shopitem;

import org.mapstruct.Mapper;
import pl.fastus.matmaster.shopitem.dto.ShopItemRequest;
import pl.fastus.matmaster.shopitem.dto.ShopItemResponse;

@Mapper(componentModel = "spring")
public interface ShopItemMapper {

    ShopItem toShopItem(ShopItemRequest request);

    ShopItemResponse toShopItemResponse(ShopItem shopItem);
}
