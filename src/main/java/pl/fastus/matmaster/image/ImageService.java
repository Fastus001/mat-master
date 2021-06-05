package pl.fastus.matmaster.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Tom - 05.06.2021
 */
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository repository;

    public Long saveImage(Image imageToSave){
        return repository.save(imageToSave).getId();
    }

    public Optional<Image> findImageById(Long id){
        return repository.findById(id);
    }
}
