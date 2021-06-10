package pl.fastus.matmaster.shopitem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.shopitem.dto.ShopItemRequest;
import pl.fastus.matmaster.shopitem.dto.ShopItemResponse;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShopItemService {

    public static final String NO_SUCH_ID_MESSAGE = "No ShopItem with given ID!!";
    private final ShopItemRepository repository;
    private final ShopItemMapper mapper;

    public List<ShopItemResponse> getAllShopItems(){
        return repository.findAll()
                .stream()
                .map(mapper::toShopItemResponse)
                .collect(Collectors.toList());
    }

    public ShopItemResponse getShopItemById(Long id) {
        return repository.findById(id)
                .map(mapper::toShopItemResponse)
                .orElseThrow(()-> new IllegalArgumentException(NO_SUCH_ID_MESSAGE));
    }

    public Long createShopItem(ShopItemRequest request){
        ShopItem shopItem = mapper.toShopItem(request);
        shopItem.setStatus(Status.ACTIVE);

        return repository.save(shopItem)
                .getId();
    }

    @Transactional
    public ShopItemResponse updateShopItem(Long id, ShopItemRequest update) {
        ShopItem shopItem = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NO_SUCH_ID_MESSAGE));
        shopItem.setName(update.getName());
        shopItem.setSubTitle(update.getSubTitle());
        shopItem.setDescription(update.getDescription());
        shopItem.setPrice(update.getPrice());

        return mapper.toShopItemResponse(shopItem);
    }

    public Long deactivate(Long id) {
        ShopItem shopItem = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NO_SUCH_ID_MESSAGE));

        shopItem.setStatus(Status.INACTIVE);

        return shopItem.getId();
    }
}
