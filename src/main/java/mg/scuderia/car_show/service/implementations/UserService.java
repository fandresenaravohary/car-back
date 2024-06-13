package mg.scuderia.car_show.service.implementations;

import mg.scuderia.car_show.model.User;
import mg.scuderia.car_show.repository.UserRepository;
import mg.scuderia.car_show.service.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User, Integer> {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public Page<User> searchUser(Specification<User> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable);
    }

}