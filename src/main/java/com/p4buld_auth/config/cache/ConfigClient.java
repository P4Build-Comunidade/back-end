package com.p4buld_auth.config.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ConfigClient {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }
}
