package tudo.hatio.hatio_tudo_task.auth.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tudo.hatio.hatio_tudo_task.auth.services.AuthFilterService;

@Configuration

@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final AuthFilterService authFilterService;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    SecurityConfiguration (AuthFilterService authFilterService,AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.authFilterService =authFilterService;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**",
                                "/api/v1/projects/home",
                                "/auth/github/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs",
                                "/swagger-ui.html/**",
                                 "/swagger-resources",
                                "/swagger-resources/**",
                                 "/configuration/ui",
                                 "/configuration/security",
                                  "/webjars/**",
                        "/swagger-ui/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)


                .addFilterBefore(authFilterService, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
