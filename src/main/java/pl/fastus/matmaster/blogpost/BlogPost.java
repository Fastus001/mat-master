package pl.fastus.matmaster.blogpost;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.paragraph.Paragraph;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom - 06.06.2021
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlogPost {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private Long headerImageId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Paragraph> paragraphs = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime creationTime;

    @Enumerated
    private Status status;
}
