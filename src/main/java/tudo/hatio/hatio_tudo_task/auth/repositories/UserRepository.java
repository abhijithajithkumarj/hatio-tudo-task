package tudo.hatio.hatio_tudo_task.auth.repositories;




import org.springframework.data.jpa.repository.JpaRepository;
import tudo.hatio.hatio_tudo_task.auth.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String username);


    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
