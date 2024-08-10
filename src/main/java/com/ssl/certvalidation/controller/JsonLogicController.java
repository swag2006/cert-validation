package com.ssl.certvalidation.controller;


import io.github.jamsesso.jsonlogic.JsonLogic;
import io.github.jamsesso.jsonlogic.JsonLogicException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class JsonLogicController {

    private final JsonLogic jsonLogic;

    public JsonLogicController() {
        this.jsonLogic = new JsonLogic();
    }

    @PostMapping("/process-rule")
    public ResponseEntity<Map<String, Object>> processRule(@RequestBody Map<String, Object> payload) {
        Map<String, Object> jsonRule = (Map<String, Object>) payload.get("json_rule");
        Map<String, Object> jsonData = (Map<String, Object>) payload.get("json_data");

        try {
            // Apply the JSON Logic rule to the data
            boolean result = (boolean) jsonLogic.apply(jsonRule, jsonData);
            return ResponseEntity.ok(Map.of("result", result));
        } catch (JsonLogicException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid rule or data: " + e.getMessage()));
        }
    }
}