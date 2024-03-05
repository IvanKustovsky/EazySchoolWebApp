package com.example.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Component("customProps")
@Data
@ConfigurationProperties(prefix = "property")
@Validated
public class CustomProps {

    @Min(value = 5, message = "Must be between 5 and 25")
    @Max(value = 25, message = "Must be between 5 and 25")
    private int pageSize;
    private Map<String, String> contact;
}
