package com.example.cameratest.controller;

import com.example.cameratest.camera.dto.CameraDto;
import com.example.cameratest.service.CameraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cameras")
public class CameraController {
    private final CameraService cameraService;

    @GetMapping()
    public List<CameraDto> getCameras() {
        log.info("Выполнено");
        return cameraService.getCameras();
    }
}
