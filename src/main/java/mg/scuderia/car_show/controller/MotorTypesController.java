package mg.scuderia.car_show.controller;

import lombok.AllArgsConstructor;
import mg.scuderia.car_show.model.MotorTypes;
import mg.scuderia.car_show.service.implementations.MotorTypesService;
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
@RequestMapping("/car_show/motorType")
@AllArgsConstructor
public class MotorTypesController {

    private final MotorTypesService motorTypesService;
    private final GenericModelSpecification<MotorTypes> genericModelSpecification;

    @PostMapping
    public ResponseEntity<MotorTypes> createMotorType(@RequestBody MotorTypes motorTypes) {
        MotorTypes createMotorType = motorTypesService.save(motorTypes);
        return ResponseEntity.ok(createMotorType);
    }

    @GetMapping
    public ResponseEntity<List<MotorTypes>> searchMotorType(
            @RequestParam(required = false) Integer motorTypeId,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int limit
    )
    {
        Specification<MotorTypes> specification = Specification.where(null);

        if (motorTypeId != null) {
            specification = specification.and(genericModelSpecification.hasInteger(motorTypeId, "motorTypeId"));
        }
        if (name != null) {
            specification = specification.and(genericModelSpecification.hasString(name, "name"));
        }

        Pageable pageable = PageRequest.of(page, limit);
        Page<MotorTypes> motorTypesPage = motorTypesService.searchMotorTypes(specification, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(motorTypesPage.getTotalElements()));

        return ResponseEntity.ok().headers(headers).body(motorTypesPage.getContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotorTypes> updateMotorType(
            @PathVariable int id,
            @RequestBody MotorTypes motorTypes
    )
    {
        MotorTypes motorType = motorTypesService.update(id, motorTypes);
        return ResponseEntity.ok(motorType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotorType(@PathVariable int id) {
        motorTypesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
