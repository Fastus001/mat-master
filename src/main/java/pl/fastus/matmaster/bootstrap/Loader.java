package pl.fastus.matmaster.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.fastus.matmaster.blogpost.BlogPostService;
import pl.fastus.matmaster.blogpost.dto.BlogPostRequest;
import pl.fastus.matmaster.paragraph.Paragraph;
import pl.fastus.matmaster.shopitem.ShopItemService;
import pl.fastus.matmaster.shopitem.dto.ShopItemRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Tom - 06.06.2021
 */
@RequiredArgsConstructor
@Component
public class Loader implements CommandLineRunner {

    private static final Long ID_PHOTO = 7L;
    private final BlogPostService blogPostService;
    private final ShopItemService shopItemService;

    @Override
    public void run(String... args) {

        loadBlogPosts();
        loadShopItems();
    }

    private void loadShopItems() {
        ShopItemRequest course = new ShopItemRequest().setName("Kurs Matematyki")
                .setSubTitle("Klasa 8")
                .setDescription("Przygotowanie do egzaminu 8 klasisty")
                .setPrice(BigDecimal.valueOf(225.25));

        ShopItemRequest course1 = new ShopItemRequest().setName("Kurs Matematyki")
                .setSubTitle("Klasa matura")
                .setDescription("Przygotowanie do matury z matematyki")
                .setPrice(BigDecimal.valueOf(499.25));

        shopItemService.createShopItem(course);
        shopItemService.createShopItem(course1);
    }

    private void loadBlogPosts() {
        Paragraph paragraph1 = Paragraph.builder().headerText("Header1").text("Ut in nunc nec elit aliquet dignissim. Vivamus eu congue ligula. Praesent sed semper metus, vel varius tortor. Duis id tincidunt elit. Maecenas nisl massa, aliquam id nulla at, ornare euismod libero. Nunc volutpat ut odio ac vulputate. Sed dictum nisl ac ipsum porttitor pulvinar. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut vel leo diam. Etiam non sollicitudin dui, quis maximus nunc. Proin lectus leo, tristique at neque nec, pellentesque cursus lacus. Fusce ultricies orci vitae felis eleifend finibus nec a turpis. Mauris gravida egestas vulputate.").imageId(ID_PHOTO).build();

        Paragraph paragraph2 = Paragraph.builder().headerText("Header2").text("Text2").imageId(ID_PHOTO).build();

        Paragraph paragraph3 = Paragraph.builder().headerText("Header3").text("Ut in nunc nec elit aliquet dignissim. Vivamus eu congue ligula. Praesent sed semper metus, vel varius tortor. Duis id tincidunt elit. Maecenas nisl massa, aliquam id nulla at, ornare euismod libero. Nunc volutpat ut odio ac vulputate. Sed dictum nisl ac ipsum porttitor pulvinar. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut vel leo diam. Etiam non sollicitudin dui, quis maximus nunc. Proin lectus leo, tristique at neque nec, pellentesque cursus lacus. Fusce ultricies orci vitae felis eleifend finibus nec a turpis. Mauris gravida egestas vulputate.").build();

        Paragraph paragraph4 = Paragraph.builder().headerText("Header2").text("Aliquam efficitur erat pulvinar lorem ultricies sollicitudin. In magna turpis, pharetra eu rhoncus et, dignissim vel nisl. Pellentesque sapien nunc, pulvinar eget molestie ut, faucibus eget mi. Donec auctor magna velit, eu scelerisque quam lobortis a. Nunc commodo dolor eu pharetra finibus. Aliquam erat volutpat. Nulla dapibus ligula ut tempor vestibulum. Suspendisse luctus imperdiet turpis, vehicula dapibus tortor sollicitudin non. Nulla facilisi. Aliquam erat volutpat. Etiam nec tellus lectus.").imageId(ID_PHOTO).build();

        BlogPostRequest blogPost = new BlogPostRequest().setTitle("Post 1")
                .setParagraphs(List.of(paragraph1, paragraph2, paragraph3))
                .setHeaderImageId(ID_PHOTO);

        BlogPostRequest blogPost1 = new BlogPostRequest().setTitle("Post 2")
                .setParagraphs(List.of(paragraph4))
                .setHeaderImageId(ID_PHOTO);


        blogPostService.createBlogPost(blogPost);
        blogPostService.createBlogPost(blogPost1);
    }
}
