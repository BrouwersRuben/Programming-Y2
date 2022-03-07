package be.kdg.java2.project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //@formatter:off
        http
            .authorizeRequests()
                .antMatchers("/", "/buildings", "/architects", "/architects/architectdetail*", "/buildings/buildingdetail*")
                    .permitAll()
                .antMatchers(HttpMethod.GET, "/api/**")
                    .permitAll()
                .regexMatchers(HttpMethod.GET, ".+\\.(css|js|map|woff2?|gif|png|jpg)(\\?.*)?")
                    .permitAll()
                .antMatchers("/h2-console/**")
                    .authenticated()
                .anyRequest()
                    .authenticated()
                .and()
                    .headers().frameOptions().disable()
                .and()
                //TODO: fix this
                .csrf()
                    .disable()
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
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
