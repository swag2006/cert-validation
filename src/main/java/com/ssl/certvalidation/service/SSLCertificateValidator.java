package com.ssl.certvalidation.service;

import com.ssl.certvalidation.model.SSLCertificateValidationResult;
import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Optional;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

@Service
public class SSLCertificateValidator {

    public SSLCertificateValidationResult validate(String url) {
        SSLCertificateValidationResult validationResult = new SSLCertificateValidationResult();

        try {
            HttpsURLConnection connection = (HttpsURLConnection) new java.net.URL(url).openConnection();
            connection.connect();
            Optional<SSLSession> session = connection.getSSLSession();
            Certificate[] certs = session.get().getPeerCertificates();

            if (certs.length > 0 && certs[0] instanceof X509Certificate certificate) {
                validationResult.setValid(true);
                validationResult.setExpirationDate(certificate.getNotAfter());
                validationResult.setStartDate(certificate.getNotBefore());
            } else {
                validationResult.setValid(false);
                validationResult.setError("No SSL certificate found");
            }

        } catch (SSLPeerUnverifiedException e) {
            validationResult.setValid(false);
            validationResult.setError("SSL peer not verified: " + e.getMessage());
        } catch (Exception e) {
            validationResult.setValid(false);
            validationResult.setError("Error occurred while validating SSL certificate: " + e.getMessage());
        }

        return validationResult;
    }


}
