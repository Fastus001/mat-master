package pl.fastus.matmaster.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.fastus.matmaster.blogpost.BlogPost;
import pl.fastus.matmaster.blogpost.BlogPostService;
import pl.fastus.matmaster.blogpost.dto.BlogPostRequest;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.paragraph.Paragraph;

import java.util.List;

/**
 * Created by Tom - 06.06.2021
 */
@RequiredArgsConstructor
@Component
public class Loader implements CommandLineRunner {

    private final BlogPostService blogPostService;

    @Override
    public void run(String... args) {

        Paragraph paragraph1 = Paragraph.builder().headerText("Header1").text("Text1").build();

        Paragraph paragraph2 = Paragraph.builder().headerText("Header2").text("Text2").build();

        BlogPostRequest blogPost = new BlogPostRequest().setTitle("Post 1")
                .setParagraphs(List.of(paragraph1, paragraph2))
                .setHeaderImageId(1L);

        BlogPostRequest blogPost1 = new BlogPostRequest().setTitle("Post 2")
                .setHeaderImageId(1L);


        blogPostService.saveBlogPost(blogPost);
        blogPostService.saveBlogPost(blogPost1);
    }
}
