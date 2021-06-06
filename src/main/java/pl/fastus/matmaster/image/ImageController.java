package pl.fastus.matmaster.image;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

/**
 * Created by Tom - 06.06.2021
 */
@RequestMapping("api/v1/image")
@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long uploadImage(@RequestParam MultipartFile multipartImage) throws IOException {
        Image image = Image.builder()
                .name(multipartImage.getName())
                .picture(multipartImage.getBytes()).build();

        return imageService.saveImage(image);
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource getImage(@PathVariable Long id) {
        final byte[] picture = imageService.findImageById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getPicture();

        return new ByteArrayResource(picture);
    }

}
