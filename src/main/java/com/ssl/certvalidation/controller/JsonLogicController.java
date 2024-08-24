package com.ssl.certvalidation.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jamsesso.jsonlogic.JsonLogic;
import io.github.jamsesso.jsonlogic.JsonLogicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class JsonLogicController {

    private final JsonLogic jsonLogic;
    private final ObjectMapper objectMapper;
    private final Logger logger = Logger.getLogger(JsonLogicController.class.getName());

    public JsonLogicController() {
        this.jsonLogic = new JsonLogic();
        this.objectMapper = new ObjectMapper(); // Jackson ObjectMapper for converting Map to JSON String
    }

    @PostMapping("/process-rule")
    public ResponseEntity<Map<String, Object>> processRule(@RequestBody Map<String, Object> payload) {
        try {
            // Convert the rule and data from Map to JSON string
            String jsonRule = objectMapper.writeValueAsString(payload.get("json_rule"));
            String jsonData = objectMapper.writeValueAsString(payload.get("json_data"));

            logger.info("Rule: " + jsonRule);
            logger.info("Data: " + jsonData);

            // Apply the JSON Logic rule to the data
            boolean result = (boolean) jsonLogic.apply(jsonRule, jsonData);
            logger.info("Result: " + result);

            return ResponseEntity.ok(Map.of("result", result));
        } catch (JsonLogicException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid rule or data: " + e.getMessage()));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error processing JSON: " + e.getMessage()));
        }
    }
}