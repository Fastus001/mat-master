package pl.fastus.matmaster.blogpost.dto;

import lombok.Data;
import pl.fastus.matmaster.paragraph.Paragraph;

import java.time.Instant;
import java.util.List;

/**
 * Created by Tom - 06.06.2021
 */
@Data
public class BlogPostResponse {

    private Long id;

    private String title;
    private Long headerImageId;
    private List<Paragraph> paragraphs;
    private Instant creationTime;
}
