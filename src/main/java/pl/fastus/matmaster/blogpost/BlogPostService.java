package pl.fastus.matmaster.blogpost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fastus.matmaster.blogpost.dto.BlogPostRequest;
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


    public Long createBlogPost(BlogPostRequest blogPostRequest){
        BlogPost toSave = mapper.toBlogPost(blogPostRequest);
        toSave.setStatus(Status.ACTIVE);
        return repository.save(toSave).getId();
    }

    public List<BlogPostResponse> getBlogPostsByStatus(Status status) {
        return repository.findAllByStatusEquals(status)
                .stream()
                .map(mapper::toBlogPostResponse)
                .collect(Collectors.toList());
    }

    public BlogPostResponse getById(Long id) {
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

    @Transactional
    public BlogPostResponse update(Long id, BlogPostRequest update) {
        BlogPost toUpdate = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No BlogPost with given id number to update!!"));

        toUpdate.setTitle(update.getTitle());
        toUpdate.setHeaderImageId(update.getHeaderImageId());
        toUpdate.setParagraphs(update.getParagraphs());

        return mapper.toBlogPostResponse(toUpdate);
    }

    public List<BlogPostResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toBlogPostResponse)
                .collect(Collectors.toList());
    }
}
