package com.ssl.certvalidation.config;

import com.ssl.certvalidation.model.SSLCertificateValidationResult;
import com.ssl.certvalidation.service.SSLCertificateValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class SSLCertValidationConfig {
    private final SSLCertificateValidator sslCertificateValidator;

    @Value("${ssl.url.to.validate}")
    private String certUrl;

    public SSLCertValidationConfig(SSLCertificateValidator sslCertificateValidator) {
        this.sslCertificateValidator = sslCertificateValidator;
    }

    @Bean
    public ApplicationRunner sslCertValidationRunner() {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                System.out.println("######### URL ####: "+certUrl);
                SSLCertificateValidationResult validationResult = sslCertificateValidator.validate(certUrl); // Replace with your URL
                if (!validationResult.isValid()) {
                    System.err.println("SSL certificate is NOT valid.");
                    System.err.println("Error: " + validationResult.getError());
                    TimeUnit.SECONDS.sleep(5); // Wait for 5 seconds before exiting
                    System.exit(1); // Exit with non-zero exit code
                }else {
                    System.out.println("******* EXPIRATION DATE ******: " + validationResult.getExpirationDate());
                }
            }
        };
    }
}
