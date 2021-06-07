package pl.fastus.matmaster.blogpost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fastus.matmaster.blogpost.dto.BlogPostResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tom - 06.06.2021
 */
@RequiredArgsConstructor
@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final BlogPostMapper mapper;


    public Long saveBlogPost(BlogPost blogPost){
        return blogPostRepository.save(blogPost).getId();
    }

    public List<BlogPostResponse> getAllBlogPosts() {
        return blogPostRepository.findAll()
                .stream()
                .map(mapper::toBlogPostResponse)
                .collect(Collectors.toList());
    }
}
