package pl.fastus.matmaster.blogpost;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.fastus.matmaster.blogpost.dto.BlogPostRequest;
import pl.fastus.matmaster.blogpost.dto.BlogPostResponse;
import pl.fastus.matmaster.enums.Status;

import java.util.List;
import java.util.Optional;

/**
 * Created by Tom - 06.06.2021
 */
@RequestMapping("/api/v1/blogpost")
@RequiredArgsConstructor
@RestController
public class BlogPostController {

    private final BlogPostService blogPostService;

    @GetMapping
    public List<BlogPostResponse> getAllByStatus(@RequestParam Optional<Status> status){
        if(status.isEmpty()){
            return blogPostService.getAll();
        } else {
            return blogPostService.getBlogPostsByStatus(status.get());
        }
    }

    @GetMapping("/{id}")
    public BlogPostResponse getBlogPostById(@PathVariable("id") Long id){
        return blogPostService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createBlogPost(@RequestBody BlogPostRequest blogPostRequest){
        return blogPostService.saveBlogPost(blogPostRequest);
    }
}
