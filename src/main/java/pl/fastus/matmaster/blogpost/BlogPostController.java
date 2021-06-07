package pl.fastus.matmaster.blogpost;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fastus.matmaster.blogpost.dto.BlogPostResponse;

import java.util.List;

/**
 * Created by Tom - 06.06.2021
 */
@RequestMapping("/api/v1/blogpost")
@RequiredArgsConstructor
@RestController
public class BlogPostController {

    private final BlogPostService blogPostService;

    @GetMapping
    public List<BlogPostResponse> getAll(){
        return blogPostService.getAllActiveBlogPosts();
    }
}
