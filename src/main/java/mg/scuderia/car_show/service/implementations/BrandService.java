package mg.scuderia.car_show.service.implementations;

import mg.scuderia.car_show.model.Brand;
import mg.scuderia.car_show.repository.BrandRepository;
import mg.scuderia.car_show.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BrandService extends GenericService<Brand, Integer> {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        super(brandRepository);
        this.brandRepository = brandRepository;
    }

    public Page<Brand> searchBrand(Specification<Brand> specification, Pageable pageable) {
        return brandRepository.findAll(specification, pageable);
    }

}