package mg.scuderia.car_show.repository;

import mg.scuderia.car_show.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
}