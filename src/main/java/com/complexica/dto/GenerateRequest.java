package com.complexica.dto;

import lombok.Data;

@Data
public class GenerateRequest {

    private String name;
    public GenerateRequest() {
    }
    public GenerateRequest(String name) {
        this.name = name;
    }
}
