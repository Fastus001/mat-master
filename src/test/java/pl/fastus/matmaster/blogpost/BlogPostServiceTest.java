package pl.fastus.matmaster.blogpost;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.paragraph.Paragraph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceTest {

    @Mock
    BlogPostRepository blogPostRepository;

    @InjectMocks
    BlogPostService blogPostService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveBlogPost() {
        Paragraph paragraph1 = Paragraph.builder().headerText("Header1").text("Text1").build();

        Paragraph paragraph2 = Paragraph.builder().headerText("Header2").text("Text2").build();

        BlogPost blogPost = BlogPost.builder().id(1L).title("Post 1").headerImageId(1L)
                .paragraphs(List.of(paragraph1,paragraph2)).status(Status.ACTIVE).build();

        given(blogPostRepository.save(any(BlogPost.class))).willReturn(blogPost);

        final Long aLong = blogPostService.saveBlogPost(blogPost);

        assertEquals(1, aLong);
    }
}
