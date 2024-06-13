package mg.scuderia.car_show.controller;

import lombok.AllArgsConstructor;
import mg.scuderia.car_show.model.Images;
import mg.scuderia.car_show.service.implementations.ImagesService;
import mg.scuderia.car_show.specification.GenericModelSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car_show/image")
@AllArgsConstructor
public class ImagesController {

    private final ImagesService imagesService;
    private final GenericModelSpecification<Images> genericModelSpecification;

    @PostMapping
    public ResponseEntity<Images> createImage(@RequestBody Images images) {
        Images createImage = imagesService.save(images);
        return ResponseEntity.ok(createImage);
    }

    @GetMapping
    public ResponseEntity<List<Images>> searchImage(
            @RequestParam(required = false) Integer imageId,
            @RequestParam(required = false) String url,
            @RequestParam(required = false) Integer carId,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int limit
    )
    {
        Specification<Images> specification = Specification.where(null);

        if (imageId != null) {
            specification = specification.and(genericModelSpecification.hasInteger(imageId, "imageId"));
        }
        if (url != null) {
            specification = specification.and(genericModelSpecification.hasString(url, "url"));
        }
        if (carId != null) {
            specification = specification.and(genericModelSpecification.hasInteger(carId, "carId"));
        }
        if (brandId != null) {
            specification = specification.and(genericModelSpecification.hasInteger(brandId, "brandId"));
        }

        Pageable pageable = PageRequest.of(page, limit);
        Page<Images> imagesPage = imagesService.searchImages(specification, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(imagesPage.getTotalElements()));

        return ResponseEntity.ok().headers(headers).body(imagesPage.getContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Images> updateImage(
            @PathVariable int id,
            @RequestBody Images images
    )
    {
        Images image = imagesService.update(id, images);
        return ResponseEntity.ok(image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable int id) {
        imagesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}