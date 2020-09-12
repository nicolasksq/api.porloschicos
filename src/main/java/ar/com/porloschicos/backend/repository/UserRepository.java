package ar.com.porloschicos.backend.repository;

import ar.com.porloschicos.backend.model.UserDao;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDao, Integer> {
    UserDao findByUsername(String username);
}
