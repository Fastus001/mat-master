package pl.fastus.matmaster.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    public static final long ID = 1L;

    @Mock
    ImageRepository imageRepository;

    @InjectMocks
    ImageService imageService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveImage() {
        Image imageToSave = Image.builder().name("image").picture("some image".getBytes()).build();
        Image savedImage = Image.builder().id(ID).name("image").picture("some image".getBytes()).build();

        given(imageRepository.save(imageToSave)).willReturn(savedImage);

        final Long imageId = imageService.saveImage(imageToSave);

        assertEquals(ID, imageId);
    }

    @Test
    void findImageById() {
        Image image = Image.builder().id(ID).name("image").picture("some image".getBytes()).build();

        given(imageRepository.findById(any())).willReturn(Optional.of(image));

        final Optional<Image> imageById = imageService.findImageById(ID);

        assertTrue(imageById.isPresent());
        assertEquals(ID, imageById.get().getId());
    }
}
