package com.example.cameratest.camera.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CameraResponse {
    private int id;
    private String urlType;
    private String videoUrl;
    private String value;
    private int ttl;
    private String sourceDataUrl;
    private String tokenDataUrl;

}
