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
        Paragraph paragraph1 = Paragraph.builder().headerText("Header1").text("Text1").build();

        Paragraph paragraph2 = Paragraph.builder().headerText("Header2").text("Text2").build();

        BlogPostRequest blogPost = new BlogPostRequest().setTitle("Post 1")
                .setParagraphs(List.of(paragraph1, paragraph2))
                .setHeaderImageId(5L);

        BlogPostRequest blogPost1 = new BlogPostRequest().setTitle("Post 2")
                .setHeaderImageId(5L);


        blogPostService.createBlogPost(blogPost);
        blogPostService.createBlogPost(blogPost1);
    }
}
