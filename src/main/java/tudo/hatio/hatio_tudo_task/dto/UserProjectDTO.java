package tudo.hatio.hatio_tudo_task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProjectDTO {
    private UUID userId;
    private UUID projectId;


}
