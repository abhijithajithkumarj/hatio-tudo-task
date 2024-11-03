package tudo.hatio.hatio_tudo_task.service;


import tudo.hatio.hatio_tudo_task.exception.model.Project;

public interface MarkdownService {
    String generateMarkdownSummary(Project project);
}
