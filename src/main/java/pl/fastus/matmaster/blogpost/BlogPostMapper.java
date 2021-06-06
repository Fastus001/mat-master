package pl.fastus.matmaster.blogpost;

import org.mapstruct.Mapper;
import pl.fastus.matmaster.blogpost.dto.BlogPostResponse;

/**
 * Created by Tom - 06.06.2021
 */
@Mapper(componentModel = "spring")
public interface BlogPostMapper {

    BlogPostResponse toBlogPostResponse(BlogPost blogPost);
}
