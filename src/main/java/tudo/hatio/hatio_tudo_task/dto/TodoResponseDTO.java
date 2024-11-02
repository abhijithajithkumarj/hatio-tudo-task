package tudo.hatio.hatio_tudo_task.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tudo.hatio.hatio_tudo_task.enums.TodoStatus;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponseDTO {
    private UUID id;
    private String description;
    private TodoStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private  UUID projectId;
}
