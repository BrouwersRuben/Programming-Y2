package be.kdg.java2.project.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class H2DatabaseConfig {
    private static final Logger log = LoggerFactory.getLogger(H2DatabaseConfig.class);

    @Bean
    public DataSource dataSource() {
        log.debug("Connection with H2 database made");
        DataSource dataSource = DataSourceBuilder.create().driverClassName("org.h2.Driver").url("jdbc:h2:mem:devDB").username("").password("").build();
        return dataSource;
    }
}
