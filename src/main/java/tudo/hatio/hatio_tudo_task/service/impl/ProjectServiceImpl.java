package tudo.hatio.hatio_tudo_task.service.impl;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tudo.hatio.hatio_tudo_task.auth.model.User;
import tudo.hatio.hatio_tudo_task.auth.repositories.UserRepository;
import tudo.hatio.hatio_tudo_task.auth.services.JwtService;
import tudo.hatio.hatio_tudo_task.config.EncryptionUtil;
import tudo.hatio.hatio_tudo_task.dto.ProjectRequestDTO;
import tudo.hatio.hatio_tudo_task.dto.ProjectResponseDTO;
import tudo.hatio.hatio_tudo_task.exception.ResourceCreationException;
import tudo.hatio.hatio_tudo_task.exception.ResourceNotFoundException;
import tudo.hatio.hatio_tudo_task.exception.UnauthorizedAccessException;
import tudo.hatio.hatio_tudo_task.exception.model.Project;
import tudo.hatio.hatio_tudo_task.repository.ProjectRepository;
import tudo.hatio.hatio_tudo_task.service.GitHubAuthTokenService;
import tudo.hatio.hatio_tudo_task.service.GitHubGistService;
import tudo.hatio.hatio_tudo_task.service.MarkdownService;
import tudo.hatio.hatio_tudo_task.service.ProjectService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final GitHubGistService gitHubGistService;
    private final MarkdownService markdownService;
    private  final GitHubAuthTokenService gitHubAuthTokenService;


    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO, String userEmail) {
        logger.info("Creating project for request: {}", projectRequestDTO);


        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> {
                    logger.error("User not found for email: {}", userEmail);
                    return new ResourceNotFoundException("User not found for email: " + userEmail);
                });

        Project project = Project.builder()
                .title(projectRequestDTO.getTitle())
                .createdDate(LocalDateTime.now())
                .todos(new ArrayList<>())
                .user(user)
                .build();

        try {
            Project savedProject = projectRepository.save(project);
            logger.info("Project saved successfully with ID: {}", savedProject.getId());
            return mapToProjectResponseDTO(savedProject);
        } catch (Exception e) {
            logger.error("Error saving project for user: {}", userEmail, e);
            throw new ResourceCreationException("Failed to save project");
        }
    }

    @Override
    public List<ProjectResponseDTO> getAllProjectsByUser(String username) {
        logger.info("Fetching user with username: {}", username);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });

        logger.info("User found: {}", user.getUsername());

        List<Project> projects = projectRepository.findByUser(user);
        logger.info("Found {} projects for user {}", projects.size(), user.getUsername());

        return projects.stream()
                .map(this::mapToProjectResponseDTO)
                .toList();
    }

    @Override
    public ProjectResponseDTO getProjectById(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Project not found with ID: {}", id);
                    return new ResourceNotFoundException("Project not found with ID: " + id);
                });

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!project.getUser().getUsername().equals(username)) {
            logger.error("User: {} is not authorized to access project with ID: {}", username, id);
            throw new UnauthorizedAccessException("You are not authorized to access this project.");
        }

        logger.info("Project found: {}", project.getTitle());
        return mapToProjectResponseDTO(project);
    }

    @Override
    public ProjectResponseDTO updateProjectById(UUID id, ProjectRequestDTO projectRequestDTO, String username) {
        logger.info("Updating project with ID: {} for user: {}", id, username);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Project not found with ID: {}", id);
                    return new ResourceNotFoundException("Project not found with ID: " + id);
                });

        if (!project.getUser().getUsername().equals(username)) {
            logger.error("User: {} is not authorized to update project with ID: {}", username, id);
            throw new UnauthorizedAccessException("You are not authorized to update this project.");
        }

        project.setTitle(projectRequestDTO.getTitle());

        Project updatedProject = projectRepository.save(project);
        logger.info("Project updated successfully with ID: {}", id);

        return mapToProjectResponseDTO(updatedProject);
    }

    @Override
    public void deleteProjectById(UUID id, String username) {
        logger.info("Deleting project with ID: {} for user: {}", id, username);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Project not found with ID: {}", id);
                    return new ResourceNotFoundException("Project not found with ID: " + id);
                });

        if (!project.getUser().getUsername().equals(username)) {
            logger.error("User: {} is not authorized to delete project with ID: {}", username, id);
            throw new UnauthorizedAccessException("You are not authorized to delete this project.");
        }

        projectRepository.delete(project);
        logger.info("Project with ID: {} deleted successfully.", id);
    }

    @Override
    public void saveGitHubToken(String username, String token) {
        logger.info("Saving GitHub token for user: {}", username);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new ResourceNotFoundException("User not found with username: " + username);
                });

        user.setGithubToken(token);
        userRepository.save(user);
        logger.info("GitHub token saved successfully for user: {}", username);
    }

    @Override
    public String exportProjectSummaryAsGist(UUID projectId) {
        logger.info("Exporting project summary as gist for project ID: {}", projectId);

        // Retrieve the project and handle the case where it is not found
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    logger.error("Project not found with ID: {}", projectId);
                    return new ResourceNotFoundException("Project not found with ID: " + projectId);
                });


        String markdownSummary = markdownService.generateMarkdownSummary(project);


        String gitToken = project.getUser().getGithubToken();
        System.out.println(gitToken);
        String decryptedToken;


        if (gitToken==null) {
            logger.warn("GitHub token is missing for user with project ID: {}. Redirecting to authorization.", projectId);
            return "REDIRECT:" + gitHubAuthTokenService.buildGitHubAuthorizationUrl();
        }
        try {
            decryptedToken = EncryptionUtil.decrypt(gitToken);
            System.out.println(decryptedToken+"ddd");
        } catch (Exception e) {
            logger.error("Error decrypting GitHub token for project ID: {}", projectId, e);
            throw new RuntimeException("Failed to decrypt GitHub token for user associated with project ID: " + projectId, e);
        }

        String gistUrl;
        try {
            gistUrl = gitHubGistService.createSecretGist(project.getTitle(), markdownSummary, decryptedToken);
        } catch (Exception e) {
            logger.error("Error creating gist for project ID: {}", projectId, e);
            throw new RuntimeException("Failed to create gist for project ID: " + projectId, e);
        }

        logger.info("Project summary exported as gist. URL: {}", gistUrl);
        return gistUrl;
    }


    private ProjectResponseDTO mapToProjectResponseDTO(Project project) {
        return modelMapper.map(project, ProjectResponseDTO.class);
    }
}