package mg.scuderia.car_show.repository;

import mg.scuderia.car_show.model.Car;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarRepository extends JpaSpecificationExecutor<Car>, GenericRepository<Car, Integer> {
    @Query(
            "SELECT c FROM Car c WHERE (:brandId IS NULL OR c.brand.brandId = :brandId) "
                + "AND (:carType IS NULL OR c.carTypes.name = :carType) "
                + "AND (:motorType IS NULL OR c.motorTypes.name = :motorType) "
                + "AND (:minCost IS NULL OR c.price >= :minCost) "
                + "AND (:maxCost IS NULL OR c.price <= :maxCost)"
    )
    List<Car> findByFilters(
            @Param("brandId") String brandId,
            @Param("carType") String carType,
            @Param("motorType") String motorType,
            @Param("minCost") BigDecimal minCost,
            @Param("maxCost") BigDecimal maxCost
    );
}