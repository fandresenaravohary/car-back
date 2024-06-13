package mg.scuderia.car_show.controller;

import lombok.AllArgsConstructor;
import mg.scuderia.car_show.model.CarTypes;
import mg.scuderia.car_show.service.implementations.CarTypesService;
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
@RequestMapping("/car_show/carType")
@AllArgsConstructor
public class CarTypesController {

    private final CarTypesService carTypesService;
    private final GenericModelSpecification<CarTypes> genericModelSpecification;

    @PostMapping
    public ResponseEntity<CarTypes> createCarType(@RequestBody CarTypes carTypes) {
        CarTypes createCarType = carTypesService.save(carTypes);
        return ResponseEntity.ok(createCarType);
    }

    @GetMapping
    public ResponseEntity<List<CarTypes>> searchCarType(
            @RequestParam(required = false) Integer carTypeId,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int limit
    )
    {
        Specification<CarTypes> specification = Specification.where(null);

        if (carTypeId != null) {
            specification = specification.and(genericModelSpecification.hasInteger(carTypeId, "carTypeId"));
        }
        if (name != null) {
            specification = specification.and(genericModelSpecification.hasString(name, "name"));
        }

        Pageable pageable = PageRequest.of(page, limit);
        Page<CarTypes> carTypesPage = carTypesService.searchCarTypes(specification, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(carTypesPage.getTotalElements()));

        return ResponseEntity.ok().headers(headers).body(carTypesPage.getContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarTypes> updateCarType(
            @PathVariable int id,
            @RequestBody CarTypes carTypes
    )
    {
        CarTypes CarType = carTypesService.update(id, carTypes);
        return ResponseEntity.ok(CarType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarType(@PathVariable int id) {
        carTypesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}