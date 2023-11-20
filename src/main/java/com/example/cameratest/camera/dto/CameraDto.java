package com.example.cameratest.camera.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CameraDto {
    private int id;
    private String urlType;
    private String videoUrl;
    private String value;
    private int ttl;
}
