package com.example.project_service.constants;

public interface SwaggerConstants {

    String UPDATE_DESCRIPTION = """
             Send which field(s) must edit.
            \s""";


    String CUSTOM_FIELD = """
            {
            "id": 0,
            "show_which_field_must_edit": "покажите_какое_поле_нужно_редактировать",
            "next_field_if_need": "следующее_поле (если_нужно)"
            }
            """;


    String PROJECT_FULL_FORM = """
            {
              "id": 0,
              "name": "String",
              "priority": "HIGH",
              "startDate": "2024-01-01",
              "deadline": "2024-12-31",
              "description": "String"
            }
            """;

    String TASK_FULL_FORM = """
            {
                "id": 0,
                "name": "string",
                "description": "string",
                "estimatedTime": "0h",
                "spentTime": "0h",
                "deadline": "2024-12-31"
            }
                      
            """;


}