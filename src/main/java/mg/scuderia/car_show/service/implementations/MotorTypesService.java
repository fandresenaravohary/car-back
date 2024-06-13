package mg.scuderia.car_show.service.implementations;

import mg.scuderia.car_show.model.MotorTypes;
import mg.scuderia.car_show.repository.MotorTypesRepository;
import mg.scuderia.car_show.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class MotorTypesService extends GenericService<MotorTypes, Integer> {

    private final MotorTypesRepository motorTypesRepository;

    public MotorTypesService(MotorTypesRepository motorTypesRepository) {
        super(motorTypesRepository);
        this.motorTypesRepository = motorTypesRepository;
    }

    public Page<MotorTypes> searchMotorTypes(Specification<MotorTypes> specification, Pageable pageable) {
        return motorTypesRepository.findAll(specification, pageable);
    }

}