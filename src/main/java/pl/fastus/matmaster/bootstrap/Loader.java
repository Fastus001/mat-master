package pl.fastus.matmaster.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.fastus.matmaster.blogpost.BlogPost;
import pl.fastus.matmaster.blogpost.BlogPostService;
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

        BlogPost blogPost = BlogPost.builder().title("Post 1")
                .paragraphs(List.of(paragraph1, paragraph2))
                .headerImageId(1L)
                .status(Status.ACTIVE).build();

        BlogPost blogPost1 = BlogPost.builder().title("Post 2")
                .headerImageId(1L)
                .status(Status.INACTIVE).build();


        blogPostService.saveBlogPost(blogPost);
        blogPostService.saveBlogPost(blogPost1);
    }
}
