package mg.scuderia.car_show.repository;

import mg.scuderia.car_show.model.Images;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends GenericRepository<Images, Integer> {}