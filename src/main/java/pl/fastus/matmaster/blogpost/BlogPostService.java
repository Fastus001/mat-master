package pl.fastus.matmaster.blogpost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fastus.matmaster.blogpost.dto.BlogPostResponse;

import java.util.List;

/**
 * Created by Tom - 06.06.2021
 */
@RequiredArgsConstructor
@Service
public class BlogPostService {

    private final BlogPostRepository repository;


    public Long saveBlogPost(BlogPost blogPost){
        return repository.save(blogPost).getId();
    }

    public List<BlogPostResponse> getAllBlogPosts() {
        //todo - get all and mapp
        return null;
    }
}
