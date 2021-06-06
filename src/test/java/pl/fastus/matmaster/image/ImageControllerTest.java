package pl.fastus.matmaster.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    ImageService imageService;

    @InjectMocks
    ImageController imageController;

    MockMvc mockMvc;

    MockMultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        multipartFile = new MockMultipartFile("multipartImage", "image.jpg",
                MediaType.IMAGE_JPEG_VALUE,"image file content".getBytes());

        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void uploadImage() throws Exception {

        mockMvc.perform(multipart("/api/v1/image").file(multipartFile))
                .andExpect(status().isCreated());
    }

    @Test
    void getImage() throws IOException {
        final Image image = Image.builder().id(1L)
                .picture("image file content".getBytes())
                .name("image.jpg").build();

        given(imageService.findImageById(any())).willReturn(Optional.of(image));

        final Resource resourceToReturn = imageController.getImage(1L);

        assertEquals(18, resourceToReturn.contentLength());
    }

    @Test
    void getImageIdNotFound(){
        given(imageService.findImageById(any())).willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, ()->imageController.getImage(any()));
    }
}
