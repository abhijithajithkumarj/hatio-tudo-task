package tudo.hatio.hatio_tudo_task.service.impl;

import org.springframework.stereotype.Service;
import tudo.hatio.hatio_tudo_task.enums.TodoStatus;
import tudo.hatio.hatio_tudo_task.model.Project;
import tudo.hatio.hatio_tudo_task.service.MarkdownService;

@Service
public class MarkdownServiceImpl implements MarkdownService {
    @Override
    public String generateMarkdownSummary(Project project) {
        StringBuilder markdown = new StringBuilder();

        markdown.append("# ").append(project.getTitle()).append("\n\n");

        long completedCount = project.getTodos().stream()
                .filter(todo -> todo.getStatus() == TodoStatus.COMPLETED)
                .count();

        markdown.append("**Summary**: ").append(completedCount).append(" / ")
                .append(project.getTodos().size()).append(" completed.\n\n");

        markdown.append("## Pending Todos\n");
        project.getTodos().stream()
                .filter(todo -> todo.getStatus() == TodoStatus.PENDING)
                .forEach(todo -> markdown.append("- [ ] ").append(todo.getDescription()).append("\n"));

        markdown.append("\n## Completed Todos\n");
        project.getTodos().stream()
                .filter(todo -> todo.getStatus() == TodoStatus.COMPLETED)
                .forEach(todo -> markdown.append("- [x] ").append(todo.getDescription()).append("\n"));

        return markdown.toString();
    }
}
