package be.kdg.java2.project.configuration;

import be.kdg.java2.project.converters.StringToBuildingTypeConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToBuildingTypeConverter());
    }

    @Bean
    public ModelMapper modelMapper(){
//        var modelMapper = new ModelMapper();
//
//        Converter<>
        return new ModelMapper();
    }
}
