package pl.fastus.matmaster.blogpost;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.fastus.matmaster.blogpost.dto.BlogPostResponse;
import pl.fastus.matmaster.enums.Status;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void getAllActive() throws Exception {
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
}
