package mg.scuderia.car_show.controller;

import lombok.AllArgsConstructor;
import mg.scuderia.car_show.model.Car;
import mg.scuderia.car_show.service.implementations.CarService;
import mg.scuderia.car_show.specification.GenericModelSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/car_show/car")
@AllArgsConstructor
@CrossOrigin
public class CarController {

    private final CarService carService;
    private final GenericModelSpecification<Car> genericModelSpecification;

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car createCar = carService.save(car);
        return ResponseEntity.ok(createCar);
    }

    @GetMapping
    public ResponseEntity<List<Car>> filterCar(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer power,
            @RequestParam(required = false) Integer placeNumber,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) String brandId,
            @RequestParam(required = false) String carTypeId,
            @RequestParam(required = false) String motorTypeId,
            @RequestParam(required = false) BigDecimal minCost,
            @RequestParam(required = false) BigDecimal maxCost,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Specification<Car> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and(genericModelSpecification.hasString(name, "name"));
        }
        if (model != null) {
            spec = spec.and(genericModelSpecification.hasString(model, "model"));
        }
        if (price != null) {
            spec = spec.and(genericModelSpecification.hasBigDecimal(price, "price"));
        }
        if (color != null) {
            spec = spec.and(genericModelSpecification.hasString(color, "color"));
        }
        if (power != null) {
            spec = spec.and(genericModelSpecification.hasInteger(power, "power"));
        }
        if (placeNumber != null) {
            spec = spec.and(genericModelSpecification.hasInteger(placeNumber, "placeNumber"));
        }
        if (status != null) {
            spec = spec.and(genericModelSpecification.hasBoolean(status, "status"));
        }
        if (brandId != null) {
            spec = spec.and(genericModelSpecification.hasInteger(Integer.valueOf(brandId), "brandId"));
        }
        if (carTypeId != null) {
            spec = spec.and(genericModelSpecification.hasInteger(Integer.valueOf(carTypeId), "carTypeId"));
        }
        if (motorTypeId != null) {
            spec = spec.and(genericModelSpecification.hasInteger(Integer.valueOf(motorTypeId), "motorTypeId"));
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Car> carPage = carService.searchCars(spec, brandId, carTypeId, motorTypeId, minCost, maxCost, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(carPage.getTotalElements()));

        return ResponseEntity.ok().headers(headers).body(carPage.getContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(
            @PathVariable int id,
            @RequestBody Car car
    ) {
        Car toUpdate = carService.update(id, car);
        return ResponseEntity.ok(toUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable int id) {
        carService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}