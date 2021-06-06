package pl.fastus.matmaster.blogpost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
