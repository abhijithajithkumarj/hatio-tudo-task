package tudo.hatio.hatio_tudo_task.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tudo.hatio.hatio_tudo_task.model.Project;
import tudo.hatio.hatio_tudo_task.model.Todo;

import java.util.List;
import java.util.UUID;
@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID> {
    List<Todo> findByProject(Project project);
}
