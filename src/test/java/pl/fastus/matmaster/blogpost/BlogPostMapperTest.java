package pl.fastus.matmaster.blogpost;

import org.junit.jupiter.api.Test;
import pl.fastus.matmaster.blogpost.dto.BlogPostResponse;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.paragraph.Paragraph;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BlogPostMapperTest {

    public static final long BLOG_ID = 1L;
    public static final long HEADER_IMAGE_ID = 22L;
    BlogPostMapper blogPostMapper = new BlogPostMapperImpl();


    @Test
    void toBlogPostResponse() {
        Paragraph paragraph1 = Paragraph.builder().headerText("Header1").text("Text1").build();

        Paragraph paragraph2 = Paragraph.builder().headerText("Header2").text("Text2").build();

        BlogPost blogPost = BlogPost.builder().id(BLOG_ID).title("Post 1")
                .paragraphs(List.of(paragraph1, paragraph2))
                .headerImageId(HEADER_IMAGE_ID)
                .status(Status.ACTIVE).build();

        BlogPostResponse blogPostResponse = blogPostMapper.toBlogPostResponse(blogPost);

        assertEquals(2, blogPostResponse.getParagraphs().size());
        assertEquals("Post 1", blogPostResponse.getTitle());
        assertEquals(BLOG_ID, blogPostResponse.getId());
        assertEquals(HEADER_IMAGE_ID, blogPostResponse.getHeaderImageId());

    }
}
