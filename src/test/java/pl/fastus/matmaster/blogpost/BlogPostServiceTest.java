package pl.fastus.matmaster.blogpost;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.fastus.matmaster.blogpost.dto.BlogPostResponse;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.paragraph.Paragraph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceTest {

    public static final long ID = 1L;
    public static final String TITLE = "Post 1";
    public static final long HEADER_IMAGE_ID = 22L;
    @Mock
    BlogPostRepository blogPostRepository;

    @Mock
    BlogPostMapper mapper;

    @InjectMocks
    BlogPostService blogPostService;

    Paragraph paragraph1;
    Paragraph paragraph2;
    BlogPost blogPost;

    @BeforeEach
    void setUp() {
        paragraph1 = Paragraph.builder().headerText("Header1").text("Text1").build();
        paragraph2 = Paragraph.builder().headerText("Header2").text("Text2").build();

        blogPost = BlogPost.builder().id(ID).title(TITLE).headerImageId(HEADER_IMAGE_ID)
                .paragraphs(List.of(paragraph1,paragraph2)).status(Status.ACTIVE).build();
    }

    @Test
    void saveBlogPost() {
        given(blogPostRepository.save(any(BlogPost.class))).willReturn(blogPost);

        final Long aLong = blogPostService.saveBlogPost(blogPost);

        assertEquals(1, aLong);
    }

    @Test
    void getAllBlogPosts() {
        BlogPostResponse blogPostResponse =
                new BlogPostResponse()
                .setId(ID)
                .setTitle(TITLE)
                .setHeaderImageId(HEADER_IMAGE_ID);

        given(blogPostRepository.findAll()).willReturn(List.of(blogPost));
        given(mapper.toBlogPostResponse(any())).willReturn(blogPostResponse);

        List<BlogPostResponse> allBlogPosts = blogPostService.getAllBlogPosts();

        assertNotNull(allBlogPosts);
        assertEquals(1, allBlogPosts.size());
        assertEquals(TITLE, allBlogPosts.get(0).getTitle());
        assertEquals(HEADER_IMAGE_ID, allBlogPosts.get(0).getHeaderImageId());
    }
}
