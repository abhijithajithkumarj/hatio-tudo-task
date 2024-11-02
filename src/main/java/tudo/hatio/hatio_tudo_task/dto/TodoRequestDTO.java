package tudo.hatio.hatio_tudo_task.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tudo.hatio.hatio_tudo_task.enums.TodoStatus;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoRequestDTO {
    @NotNull(message = "task description is mandatory")
    private String description;
    @NotNull(message = "Todo status is mandatory : (PENDING or COMPLETED)")
    private TodoStatus status;
    @NotNull(message = "ProjectID is mandatory")
    private UUID projectId;
}
