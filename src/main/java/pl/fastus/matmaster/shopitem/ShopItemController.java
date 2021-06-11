package pl.fastus.matmaster.shopitem;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.fastus.matmaster.shopitem.dto.ShopItemResponse;

import java.util.List;

@RequestMapping("/api/v1/shop-item")
@RequiredArgsConstructor
@RestController
public class ShopItemController {

    private final ShopItemService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShopItemResponse> getAllShopItems(){
        return service.getAllShopItems();
    }
}
