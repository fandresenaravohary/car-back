package mg.scuderia.car_show.controller;

import lombok.AllArgsConstructor;
import mg.scuderia.car_show.model.Brand;
import mg.scuderia.car_show.service.implementations.BrandService;
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
@RequestMapping("/car_show/brand")
@AllArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final GenericModelSpecification<Brand> genericModelSpecification;

    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        Brand createdBrand = brandService.save(brand);
        return ResponseEntity.ok(createdBrand);
    }

    @GetMapping
    public ResponseEntity<List<Brand>> searchBrand(
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int limit
    )
    {
        Specification<Brand> specification = Specification.where(null);

        if (brandId != null) {
            specification = specification.and(genericModelSpecification.hasInteger(brandId, "brandId"));
        }
        if (name != null) {
            specification = specification.and(genericModelSpecification.hasString(name, "name"));
        }

        Pageable pageable = PageRequest.of(page, limit);
        Page<Brand> brandPage = brandService.searchBrand(specification, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(brandPage.getTotalElements()));

        return ResponseEntity.ok().headers(headers).body(brandPage.getContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(
            @PathVariable int id,
            @RequestBody Brand brand
    )
    {
        Brand updatedBrand = brandService.update(id, brand);
        return ResponseEntity.ok(updatedBrand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable int id) {
        brandService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}