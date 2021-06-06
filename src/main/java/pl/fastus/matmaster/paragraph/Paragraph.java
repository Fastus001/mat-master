package pl.fastus.matmaster.paragraph;

import lombok.*;
import pl.fastus.matmaster.blogpost.BlogPost;

import javax.persistence.*;

/**
 * Created by Tom - 06.06.2021
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Paragraph {

    @Id
    @GeneratedValue
    private Long id;

    private String headerText;

    @Lob
    private String text;

    private Long imageId;
}
