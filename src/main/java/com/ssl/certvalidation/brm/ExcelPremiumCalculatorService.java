package com.ssl.certvalidation.brm;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Service
public class ExcelPremiumCalculatorService {


    private final FileLoaderService fileLoaderService;

    public ExcelPremiumCalculatorService(FileLoaderService fileLoaderService) {
        this.fileLoaderService = fileLoaderService;
    }

    public double calculateFinalPremium(String excelFileName, PremiumInput input) {
        double finalPremium = 0.0;
        log.info("premium input values:  {} ", input);
        try {
            FileInputStream file = new FileInputStream(fileLoaderService.loadFile(excelFileName));
            Workbook workbook = new XSSFWorkbook(file);

            // Assume the data is in the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Update input fields
            setCellValue(sheet, "CustomerAge", input.getCustomerAge());
            setCellValue(sheet, "Accidents", input.getAccidents());
            setCellValue(sheet, "Violations", input.getViolations());
            setCellValue(sheet, "VehicleType", input.getVehicleType());
            setCellValue(sheet, "VehicleValue", input.getVehicleValue());
            setCellValue(sheet, "LocationRisk", input.getLocationRisk());
            setCellValue(sheet, "BasePremium", input.getBasePremium());
            setCellValue(sheet, "LoyaltyYears", input.getLoyaltyYears());

            // Evaluate the formula for the final premium
            Cell finalPremiumCell = sheet.getRow(sheet.getLastRowNum()).getCell(3); // Assuming final premium is in the last row, column D
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            finalPremium = evaluator.evaluate(finalPremiumCell).getNumberValue();
            log.info("calculated premium: {}",finalPremium);
            workbook.close();
            file.close();
        } catch (IOException e) {
            log.info("Exception occurred while calculating finalPremium: {}", e.getMessage());
            e.printStackTrace();
        }

        return finalPremium;
    }

    private void setCellValue(Sheet sheet, String namedRange, Object value) {
        Name namedCell = sheet.getWorkbook().getName(namedRange);
        CellReference cellReference = new CellReference(namedCell.getRefersToFormula());
        Row row = sheet.getRow(cellReference.getRow());
        Cell cell = row.getCell(cellReference.getCol());

        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }
    }
}
