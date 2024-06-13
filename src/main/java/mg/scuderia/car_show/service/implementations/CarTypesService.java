package mg.scuderia.car_show.service.implementations;

import mg.scuderia.car_show.model.CarTypes;
import mg.scuderia.car_show.repository.CarTypesRepository;
import mg.scuderia.car_show.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CarTypesService extends GenericService<CarTypes, Integer> {

    private final CarTypesRepository carTypesRepository;

    public CarTypesService(CarTypesRepository carTypesRepository) {
        super(carTypesRepository);
        this.carTypesRepository = carTypesRepository;
    }

    public Page<CarTypes> searchCarTypes(Specification<CarTypes> specification, Pageable pageable) {
        return carTypesRepository.findAll(specification, pageable);
    }

}