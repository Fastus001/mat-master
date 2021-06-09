package pl.fastus.matmaster.shopitem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fastus.matmaster.shopitem.dto.ShopItemResponse;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShopItemService {

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
                .orElseThrow(()-> new IllegalArgumentException("No ShopItem with given ID!!"));
    }
}
