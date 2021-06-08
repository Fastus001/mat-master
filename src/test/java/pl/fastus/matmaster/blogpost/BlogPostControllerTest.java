package pl.fastus.matmaster.blogpost;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.fastus.matmaster.blogpost.dto.BlogPostRequest;
import pl.fastus.matmaster.blogpost.dto.BlogPostResponse;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.paragraph.Paragraph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BlogPostControllerTest {

    @Mock
    BlogPostService service;

    @InjectMocks
    BlogPostController controller;

    MockMvc mockMvc;

    List<BlogPostResponse> responses;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        responses = List.of(new BlogPostResponse().setId(1L).setTitle("Title1").setHeaderImageId(10L),
                new BlogPostResponse().setId(2L).setTitle("Title2").setHeaderImageId(11L),
                new BlogPostResponse().setId(3L).setTitle("Title3").setHeaderImageId(12L));
    }

    @Test
    void getAll() throws Exception {
        given(service.getAll()).willReturn(responses);

        mockMvc.perform(get("/api/v1/blogpost"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Title1")))
                .andExpect(jsonPath("$[0].headerImageId", is(10)))
                .andExpect(jsonPath("$[2].title", is("Title3")));

        verify(service).getAll();
        verify(service, times(0)).getBlogPostsByStatus(any());
    }

    @Test
    void getAllByStatusActive() throws Exception {
        given(service.getBlogPostsByStatus(Status.ACTIVE)).willReturn(responses.subList(0,1));

        mockMvc.perform(get("/api/v1/blogpost?status=ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Title1")))
                .andExpect(jsonPath("$[0].headerImageId", is(10)));

        verify(service, times(0)).getAll();
        verify(service, times(1)).getBlogPostsByStatus(any());
    }

    @Test
    void getBlogPostById() throws Exception {
        given(service.getById(any())).willReturn(responses.get(2));

        mockMvc.perform(get("/api/v1/blogpost/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.title", is("Title3")))
                .andExpect(jsonPath("$.headerImageId", is(12)));

        verify(service, times(1)).getById(any());
    }

    @Test
    void createBlogPost() throws Exception {
        BlogPostRequest request = new BlogPostRequest()
                .setTitle("Title")
                .setParagraphs(
                        List.of(new Paragraph(), new Paragraph())
                )
                .setHeaderImageId(25L);

        given(service.saveBlogPost(any(BlogPostRequest.class))).willReturn(1L);

        String content = mockMvc.perform(post("/api/v1/blogpost")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("1", content);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() throws Exception {
        BlogPostRequest request = new BlogPostRequest()
                .setTitle("Title")
                .setParagraphs(List.of(new Paragraph(), new Paragraph())                )
                .setHeaderImageId(25L);

        BlogPostResponse response = new BlogPostResponse()
                .setId(1L)
                .setTitle("Title")
                .setParagraphs(List.of(new Paragraph(), new Paragraph()))
                .setHeaderImageId(25L);


        given(service.update(any(), any(BlogPostRequest.class))).willReturn(response);

        mockMvc.perform(put("/api/v1/blogpost/1")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.headerImageId", is(25)));
    }
}
