package pl.fastus.matmaster.paragraph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Created by Tom - 06.06.2021
 */
@NoArgsConstructor
@AllArgsConstructor
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
