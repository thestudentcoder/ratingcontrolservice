package com.tdd.book.rating.control.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RatingControlServiceConfig {

    @Value("${api.bookService.endpoint}")
    private String bookServiceEndpoint;

}
