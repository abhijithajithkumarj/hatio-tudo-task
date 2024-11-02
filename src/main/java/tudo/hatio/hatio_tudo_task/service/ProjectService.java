package tudo.hatio.hatio_tudo_task.service;



import tudo.hatio.hatio_tudo_task.dto.ProjectRequestDTO;
import tudo.hatio.hatio_tudo_task.dto.ProjectResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO, String username);

    List<ProjectResponseDTO> getAllProjectsByUser(String username);

    ProjectResponseDTO getProjectById(UUID id);

    ProjectResponseDTO updateProjectById(UUID id, ProjectRequestDTO projectRequestDTO, String username);

    void deleteProjectById(UUID id, String username);

    void saveGitHubToken(String username, String token);

    String exportProjectSummaryAsGist(UUID projectId);
}
