package com.example.cameratest.camera.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TokenDataDto {
    private String value;
    private int ttl;
}
