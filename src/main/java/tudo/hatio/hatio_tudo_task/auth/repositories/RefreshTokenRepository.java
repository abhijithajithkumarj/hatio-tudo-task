package tudo.hatio.hatio_tudo_task.auth.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import tudo.hatio.hatio_tudo_task.auth.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
