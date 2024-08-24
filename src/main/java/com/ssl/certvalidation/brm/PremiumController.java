package com.ssl.certvalidation.brm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/premium")
public class PremiumController {

    private final String excelFileName = "Auto_Insurance_Premium_Calculation.xlsx";

    final
    ExcelPremiumCalculatorService excelPremiumCalculatorService;

    public PremiumController(ExcelPremiumCalculatorService excelPremiumCalculatorService) {
        this.excelPremiumCalculatorService = excelPremiumCalculatorService;
    }


    @PostMapping("/calculate")
    public double calculatePremium(@RequestBody PremiumInput input) {
        return excelPremiumCalculatorService.calculateFinalPremium(excelFileName, input);
    }
}