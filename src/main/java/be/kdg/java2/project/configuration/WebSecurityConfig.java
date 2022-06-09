package be.kdg.java2.project.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .authorizeRequests()
                .antMatchers("/")
                    .permitAll()
                .antMatchers("/buildings/add", "/architects/add")
                    .hasAnyRole("ROLE_CREATOR")
                .regexMatchers(HttpMethod.GET, ".+\\.(css|js|map|woff2?|gif|png|jpg)(\\?.*)?")
                    .permitAll()
                .antMatchers("/h2-console/**")
                    .authenticated()
                .anyRequest()
                    .authenticated()
                .and()
                    .headers().frameOptions().disable()
                .and()
                .csrf()
                    .csrfTokenRepository(
                            CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
            .formLogin()
                .loginPage("/login")
                    .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .httpBasic()
                .authenticationEntryPoint(httpStatusEntryPoint());
        //@formatter:on
    }

    private HttpStatusEntryPoint httpStatusEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_CREATOR > ROLE_UPDATER  > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }
}
