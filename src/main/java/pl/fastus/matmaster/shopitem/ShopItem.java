package pl.fastus.matmaster.shopitem;

import lombok.*;
import pl.fastus.matmaster.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ShopItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String subTitle;

    @Lob
    private String description;

    private BigDecimal price;

    @Enumerated(value = EnumType.STRING)
    private Status status;
}
