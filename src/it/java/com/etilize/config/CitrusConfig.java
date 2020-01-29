package com.etilize.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.http.client.HttpClientBuilder;
import com.consol.citrus.report.MessageTracingTestListener;

/**
 * Configuration class that houses all the configs for citrus integration test framework
 *
 * @author Faisal Feroz
 *
 */
@EnableConfigurationProperties(IntegrationTestProperties.class)
@Configuration
public class CitrusConfig {

    @Autowired
    private IntegrationTestProperties config;

    /**
     * {@link MessageTracingTestListener}
     *
     * @return MessageTracingTestListener
     */
    @Bean
    MessageTracingTestListener messageTracingListener() {
        return new MessageTracingTestListener();
    }

    /**
     * {@link HttpClient} for interacting with the service
     *
     * @return {@link HttpClient}
     */
    @Bean
    HttpClient serviceClient() {
        return new HttpClientBuilder() //
                .requestUrl(config.getServiceUrl()) //
                .build();
    }

}
