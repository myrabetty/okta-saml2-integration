package com.example.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Data
@Configuration
@ConfigurationProperties("app.security.saml2")
public class Saml2Properties {

    private IdentityProvider identityProvider;
    private Signing signing;

    public String audienceUri;

    @Data
    public static class IdentityProvider {
        private String entityId;
        private String ssoUrl;
        private Verification verification;
    }

    @Data
    public static class Verification {
        private Jks jks;
    }

    @Data
    public static class Jks {
        private Resource keyStoreLocation;
        private String keyStorePassword;
        private String keyStoreAlias;
    }

    @Data
    public static class Signing {
        private Jks jks;
    }

}

