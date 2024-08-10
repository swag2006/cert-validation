# cert-validation
Validate the ssl certificates on the startup of the spring boot.

```shell
curl -X POST http://localhost:8080/api/process-rule \
-H "Content-Type: application/json" \
-d '{
  "json_rule": {
    "and": [
      { ">" : [ { "var" : "age" }, 24 ] },
      { "<=" : [ { "var" : "age" }, 60 ] },
      { ">=": [ { "var": "credit_score" }, 700 ] },
      { ">=": [ { "var": "monthly_income" }, 3000 ] },
      {
        "if": [
          { "var": "existing_loans" },
          { "<=": [ 
            { "reduce": [
                { "var": "existing_loans" },
                { "+": [ { "var": "accumulator" }, { "var": "current" } ] },
                0
              ] 
            },
            { "*": [ { "var": "monthly_income" }, 0.5 ] }
          ] },
          true
        ]
      }
    ]
  },
  "json_data": {
    "age": 30,
    "credit_score": 720,
    "monthly_income": 4000,
    "existing_loans": [500, 1000]
  }
}'
```