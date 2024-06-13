package mg.scuderia.car_show.service.implementations;

import mg.scuderia.car_show.model.Car;
import mg.scuderia.car_show.repository.CarRepository;
import mg.scuderia.car_show.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CarService extends GenericService<Car, Integer> {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        super(carRepository);
        this.carRepository = carRepository;
    }

    public Page<Car> searchCars(
            Specification<Car> spec,
            String brandId,
            String carType,
            String motorType,
            BigDecimal minCost,
            BigDecimal maxCost,
            Pageable pageable
    ) {

        if ((minCost != null && maxCost == null) || (minCost == null && maxCost != null)) {
            throw new IllegalArgumentException("Parameters minCost and maxCost must be provided together.");
        }

        Specification<Car> combinedSpec = spec;

        if (brandId != null) {
            combinedSpec = combinedSpec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("brandId"), brandId));
        }
        if (carType != null) {
            combinedSpec = combinedSpec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("carTypeId"), carType));
        }
        if (motorType != null) {
            combinedSpec = combinedSpec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("motorTypeId"), motorType));
        }
        if (minCost != null && maxCost != null) {
            combinedSpec = combinedSpec.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), minCost, maxCost));
        }

        return carRepository.findAll(combinedSpec, pageable);
    }
}
