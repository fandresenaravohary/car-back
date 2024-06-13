package mg.scuderia.car_show.service.implementations;

import mg.scuderia.car_show.model.Images;
import mg.scuderia.car_show.repository.ImagesRepository;
import mg.scuderia.car_show.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ImagesService extends GenericService<Images, Integer> {

    private final ImagesRepository imagesRepository;

    public ImagesService(ImagesRepository imagesRepository) {
        super(imagesRepository);
        this.imagesRepository = imagesRepository;
    }

    public Page<Images> searchImages(Specification<Images> specification, Pageable pageable) {
        return imagesRepository.findAll(specification, pageable);
    }

}