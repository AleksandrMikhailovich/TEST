package com.example.cameratest.feignClient;

import com.example.cameratest.camera.response.CameraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "camera-service", url = "https://run.mocky.io/v3/bc34ce01-90c6-4266-93f1-07591afad12e")
public interface CameraFeignClient {
    @GetMapping()
    List<CameraResponse> getCameras();
}
