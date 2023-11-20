package com.example.cameratest.camera.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SourceDataResponse {
    private String urlType;
    private String videoUrl;
}
