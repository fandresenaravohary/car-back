package mg.scuderia.car_show.repository;

import mg.scuderia.car_show.model.Brand;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends GenericRepository<Brand, Integer> {}