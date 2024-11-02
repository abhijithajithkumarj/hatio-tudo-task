package tudo.hatio.hatio_tudo_task.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tudo.hatio.hatio_tudo_task.dto.ProjectRequestDTO;
import tudo.hatio.hatio_tudo_task.dto.ProjectResponseDTO;
import tudo.hatio.hatio_tudo_task.dto.TokenDTO;
import tudo.hatio.hatio_tudo_task.service.ProjectService;

import java.util.List;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;
    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);


    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid@ RequestBody ProjectRequestDTO projectRequestDTO,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        ProjectResponseDTO response = projectService.createProject(projectRequestDTO,userDetails.getUsername());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/home")
    public ResponseEntity<String> sampleTestApi(){

     return  new ResponseEntity<>("welcome to  taskstream",HttpStatus.OK);
    }
    @GetMapping
    public List<ProjectResponseDTO> getAllProjectsByUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return projectService.getAllProjectsByUser(username);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable UUID id) {
        ProjectResponseDTO projectResponseDTO = projectService.getProjectById(id);
        return ResponseEntity.ok(projectResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProjectById(@PathVariable UUID id,
                                                                @Valid  @RequestBody ProjectRequestDTO projectRequestDTO,
                                                                @AuthenticationPrincipal UserDetails userDetails) {
        ProjectResponseDTO updatedProject = projectService.updateProjectById(id, projectRequestDTO, userDetails.getUsername());
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectById(@PathVariable UUID id,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        projectService.deleteProjectById(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/github/tokens")
    public ResponseEntity<String> saveGitHubToken(@RequestBody TokenDTO token) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        projectService.saveGitHubToken(username, token.getToken());
        return ResponseEntity.ok("GitHub token saved successfully.");
    }

    @PostMapping("/{projectId}/export")
    public ResponseEntity<String> exportProjectSummary(@PathVariable UUID projectId) {

        String result = projectService.exportProjectSummaryAsGist(projectId);
        logger.info(result+"---------------------");

        if (result.startsWith("REDIRECT:")) {
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", result.substring(9)).build();
        }

        return ResponseEntity.ok(result);

    }







}
