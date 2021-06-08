package pl.fastus.matmaster.blogpost.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import pl.fastus.matmaster.paragraph.Paragraph;

import java.util.List;

@Accessors(chain = true)
@Data
public class BlogPostRequest {
    private String title;
    private Long headerImageId;
    private List<Paragraph> paragraphs;
}
