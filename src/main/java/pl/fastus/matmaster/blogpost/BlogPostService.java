package pl.fastus.matmaster.blogpost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fastus.matmaster.blogpost.dto.BlogPostResponse;
import pl.fastus.matmaster.enums.Status;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tom - 06.06.2021
 */
@RequiredArgsConstructor
@Service
public class BlogPostService {

    private final BlogPostRepository repository;
    private final BlogPostMapper mapper;


    public Long saveBlogPost(BlogPost blogPost){
        return repository.save(blogPost).getId();
    }

    public List<BlogPostResponse> getAllActiveBlogPosts() {
        return repository.findAllByStatusEquals(Status.ACTIVE)
                .stream()
                .map(mapper::toBlogPostResponse)
                .collect(Collectors.toList());
    }

    public BlogPostResponse getBlogPostById(Long id) {
        return repository.findById(id)
                .map(mapper::toBlogPostResponse)
                .orElseThrow(()->new IllegalArgumentException("No BlogPost with given id number!!"));
    }

    @Transactional
    public Long disableById(Long id) {
        BlogPost toDisable = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No BlogPost with given id number!!"));

        toDisable.setStatus(Status.INACTIVE);

        return toDisable.getId();
    }
}
