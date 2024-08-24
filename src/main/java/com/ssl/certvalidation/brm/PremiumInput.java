package com.ssl.certvalidation.brm;

import lombok.Data;

@Data
public class PremiumInput {
    private int customerAge;
    private int accidents;
    private int violations;
    private String vehicleType;
    private double vehicleValue;
    private String locationRisk;
    private double basePremium;
    private int loyaltyYears;
}
