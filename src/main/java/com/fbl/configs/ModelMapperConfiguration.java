package com.fbl.configs;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Model Mapper Configuration
 * 
 * @author Sam Butler
 * @since Jul 09, 2024
 */
@Configuration
public class ModelMapperConfiguration {
    @Bean
    ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}
