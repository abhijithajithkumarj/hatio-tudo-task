package tudo.hatio.hatio_tudo_task.service;



import tudo.hatio.hatio_tudo_task.dto.TodoRequestDTO;
import tudo.hatio.hatio_tudo_task.dto.TodoResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TodoService {
    TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO);

    List<TodoResponseDTO> getTodosByProject(UUID projectId, String username);

    TodoResponseDTO updateTodo(UUID todoId, UUID id, TodoRequestDTO todoRequestDTO, String username);

    void deleteTodoFromProject(UUID todoId, UUID projectId, String username);
}
