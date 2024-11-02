package tudo.hatio.hatio_tudo_task.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tudo.hatio.hatio_tudo_task.auth.model.User;
import tudo.hatio.hatio_tudo_task.model.Project;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByUserUsername(String username);

    List<Project> findByUser(User user);
}
