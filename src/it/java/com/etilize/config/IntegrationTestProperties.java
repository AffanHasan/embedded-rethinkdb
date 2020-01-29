package com.etilize.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Integration Test properties
 *
 * @author Faisal Feroz
 *
 */
@Component
@ConfigurationProperties("integration.test")
public class IntegrationTestProperties {

    /**
     * Url at which the deployed service is accessible
     */
    private String serviceUrl = "http://localhost:8080";

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

}
