package com.ssl.certvalidation.model;

import lombok.Data;

import java.util.Date;

@Data
public  class SSLCertificateValidationResult {
    private boolean valid;
    private Date startDate;
    private Date expirationDate;
    private String error;

}