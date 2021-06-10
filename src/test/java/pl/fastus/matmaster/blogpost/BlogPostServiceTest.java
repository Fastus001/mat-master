package pl.fastus.matmaster.blogpost;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.fastus.matmaster.blogpost.dto.BlogPostRequest;
import pl.fastus.matmaster.blogpost.dto.BlogPostResponse;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.paragraph.Paragraph;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceTest {

    public static final long ID = 1L;
    public static final String TITLE = "Post 1";
    public static final long HEADER_IMAGE_ID = 22L;
    public static final long HEADER_IMAGE_ID_UPDATED = 10L;
    @Mock
    BlogPostRepository repository;

    @Mock
    BlogPostMapper mapper;

    @InjectMocks
    BlogPostService service;

    Paragraph paragraph1;
    Paragraph paragraph2;
    BlogPost blogPost;
    BlogPost inactiveBlogPost;
    BlogPostResponse responseToReturn;

    @BeforeEach
    void setUp() {
        paragraph1 = Paragraph.builder().id(2L).headerText("Header1").text("Text1").build();
        paragraph2 = Paragraph.builder().id(3L).headerText("Header2").text("Text2").build();

        blogPost = BlogPost.builder().id(ID).title(TITLE).headerImageId(HEADER_IMAGE_ID)
                .paragraphs(List.of(paragraph1,paragraph2)).status(Status.ACTIVE).build();
        inactiveBlogPost = BlogPost.builder().id(2L).status(Status.INACTIVE).build();

        responseToReturn = new BlogPostResponse().setId(ID).setTitle(TITLE)
                                                           .setHeaderImageId(HEADER_IMAGE_ID);
    }

    @Test
    void deactivateBlogPost(){
        given(repository.findById(ID)).willReturn(Optional.of(blogPost));

        Long blogPostId = service.disableById(ID);

        assertEquals(ID, blogPostId);
        assertEquals(Status.INACTIVE, blogPost.getStatus());
    }

    @Test
    void getBlogPostById(){
        given(repository.findById(ID)).willReturn(Optional.of(blogPost));
        given(mapper.toBlogPostResponse(blogPost)).willReturn(responseToReturn);

        BlogPostResponse blogPost = service.getById(ID);

        assertEquals(ID, blogPost.getId());
    }

    @Test
    void getBlogPostByIdWithWrongId(){
        given(repository.findById(any())).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, ()-> service.getById(20L));
    }

    @Test
    void createBlogPost() {
        BlogPostRequest request = new BlogPostRequest()
                .setTitle("New title");

        given(mapper.toBlogPost(any())).willReturn(blogPost);
        given(repository.save(any(BlogPost.class))).willReturn(blogPost);

        final Long aLong = service.createBlogPost(request);

        assertEquals(1, aLong);
    }

    @Test
    void getAllActiveBlogPosts() {
        given(repository.findAllByStatusEquals(Status.ACTIVE)).willReturn(List.of(blogPost));
        given(mapper.toBlogPostResponse(any())).willReturn(responseToReturn);

        List<BlogPostResponse> allBlogPosts = service.getBlogPostsByStatus(Status.ACTIVE);

        assertNotNull(allBlogPosts);
        assertEquals(1, allBlogPosts.size());
        assertEquals(TITLE, allBlogPosts.get(0).getTitle());
        assertEquals(HEADER_IMAGE_ID, allBlogPosts.get(0).getHeaderImageId());
    }

    @Test
    void getAllBlogPosts() {
        given(repository.findAll()).willReturn(List.of(blogPost, inactiveBlogPost));
        given(mapper.toBlogPostResponse(any())).willReturn(responseToReturn);

        List<BlogPostResponse> allBlogPosts = service.getAll();

        assertNotNull(allBlogPosts);
        assertEquals(2, allBlogPosts.size());
    }

    @Test
    void updateBlogPostTitle(){
        BlogPostRequest update = new BlogPostRequest().setTitle("New title");
        BlogPostResponse response = new BlogPostResponse().setTitle("New title");

        given(repository.findById(any())).willReturn(Optional.of(blogPost));
        given(mapper.toBlogPostResponse(any())).willReturn(response);

        BlogPostResponse updatedResponse = service.update(ID,update);

        assertEquals("New title", updatedResponse.getTitle());
    }

    @Test
    void updateBlogPostHeaderImage(){
        BlogPostRequest update = new BlogPostRequest()
                .setHeaderImageId(HEADER_IMAGE_ID_UPDATED);
        BlogPostResponse response = new BlogPostResponse()
                .setHeaderImageId(HEADER_IMAGE_ID_UPDATED);

        given(repository.findById(any())).willReturn(Optional.of(blogPost));
        given(mapper.toBlogPostResponse(any())).willReturn(response);

        BlogPostResponse updatedResponse = service.update(ID,update);

        assertEquals(HEADER_IMAGE_ID_UPDATED, updatedResponse.getHeaderImageId());
    }

    @Test
    void updateBlogPostParagraphs(){
        Paragraph updateParagraph = Paragraph.builder().id(2L).headerText("New Header").text("New Text1").build();
        Paragraph updateParagraph1 = Paragraph.builder().id(3L).headerText("Header2").text("Text2").build();
        Paragraph updateParagraph2 = Paragraph.builder().id(4L).headerText("Header2").text("Text2").build();


        BlogPostRequest update = new BlogPostRequest()
                .setParagraphs(List.of(updateParagraph, updateParagraph1, updateParagraph2));
        BlogPostResponse response = new BlogPostResponse()
                .setParagraphs(List.of(updateParagraph, updateParagraph1, updateParagraph2));

        given(repository.findById(any())).willReturn(Optional.of(blogPost));
        given(mapper.toBlogPostResponse(any())).willReturn(response);

        BlogPostResponse updatedResponse = service.update(ID,update);
        List<Paragraph> paragraphs = updatedResponse.getParagraphs();

        assertEquals(3, paragraphs.size());
        assertEquals("New Header", paragraphs.get(0).getHeaderText());
    }
}
