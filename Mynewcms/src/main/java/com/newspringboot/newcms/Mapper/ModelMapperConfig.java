package com.newspringboot.newcms.Mapper;

import com.newspringboot.newcms.DTO.CourseDTO;
import com.newspringboot.newcms.Model.Course;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Course.class, CourseDTO.class).addMappings(mapper -> {
            mapper.map(Course::getName, CourseDTO::setName);
            // Add more mappings if needed
        });
        return modelMapper;
    }
}
