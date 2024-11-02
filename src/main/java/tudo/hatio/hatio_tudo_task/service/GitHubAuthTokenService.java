package tudo.hatio.hatio_tudo_task.service;

public interface GitHubAuthTokenService {
    void processGitHubCallback(String code, String state);
     String buildGitHubAuthorizationUrl();
    String getGitHubAccessToken(String authorizationCode);
}
