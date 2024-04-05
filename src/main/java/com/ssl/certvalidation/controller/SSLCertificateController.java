package com.ssl.certvalidation.controller;
// WeatherController.java
import com.ssl.certvalidation.model.SSLCertificateValidationResult;
import com.ssl.certvalidation.service.SSLCertificateValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSLCertificateController {

    final
    SSLCertificateValidator validator;

    public SSLCertificateController(SSLCertificateValidator validator) {
        this.validator = validator;
    }

    @GetMapping("/cert/validity")
    public SSLCertificateValidationResult getSSLValidity(@RequestParam String url) {
        System.out.println(url);
        return validator.validate(url);
    }
}
