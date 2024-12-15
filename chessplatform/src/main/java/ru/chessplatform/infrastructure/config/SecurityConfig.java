package ru.chessplatform.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import ru.chessplatform.domain.service.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthService authService;

    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**/admin/**", "/player/**").hasRole("ADMIN")
                .antMatchers("/player/**").hasRole("PLAYER")
                .antMatchers("/auth/**", "/", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login").permitAll()
                .failureUrl("/auth/login?error=true")
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(email -> {
            return authService.getUserByEmail(email)
                    .map(player -> org.springframework.security.core.userdetails.User.builder()
                            .username(player.getEmail())
                            .password("{noop}" + player.getPassword())
                            .roles(player.getRole().name())
                            .build()
                    )
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        });
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl() {
            @Override
            public void handle(
                    javax.servlet.http.HttpServletRequest request,
                    javax.servlet.http.HttpServletResponse response,
                    AccessDeniedException accessDeniedException) throws java.io.IOException, javax.servlet.ServletException {
                response.sendRedirect("/auth/forbidden");
            }
        };
    }
}
