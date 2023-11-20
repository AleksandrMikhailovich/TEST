package com.example.cameratest.service;

import com.example.cameratest.aggregator.CameraDataAggregator;
import com.example.cameratest.camera.dto.CameraDto;
import com.example.cameratest.camera.response.CameraResponse;
import com.example.cameratest.camera.response.SourceDataResponse;
import com.example.cameratest.camera.response.TokenDataResponse;
import com.example.cameratest.feignClient.CameraFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CameraService {

    private final CameraFeignClient cameraFeignClient;
    private final CameraDataAggregator cameraDataAggregator;

    public List<CameraDto> getCameras() {
        List<CameraResponse> cameraResponses = cameraFeignClient.getCameras();

        // Асинхронно выполняем запросы к внешним сервисам для получения данных и токенов
        List<CompletableFuture<CameraDto>> futures = cameraResponses.stream().map(this::asyncFetchDataAndToken).collect(Collectors.toList());

        // Собираем результаты
        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public CompletableFuture<CameraDto> asyncFetchDataAndToken(CameraResponse cameraResponse) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                SourceDataResponse sourceDataResponse = cameraDataAggregator.getSourceData(cameraResponse.getSourceDataUrl());
                TokenDataResponse tokenDataResponse = cameraDataAggregator.getTokenData(cameraResponse.getTokenDataUrl());

                return aggregateCameraData(cameraResponse, sourceDataResponse, tokenDataResponse);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });

    }

    private CameraDto aggregateCameraData(CameraResponse cameraResponse, SourceDataResponse sourceDataResponse, TokenDataResponse tokenDataResponse) {
        CameraDto cameraDto = new CameraDto();
        cameraDto.setId(cameraResponse.getId());
        cameraDto.setUrlType(sourceDataResponse.getUrlType());
        cameraDto.setVideoUrl(sourceDataResponse.getVideoUrl());
        cameraDto.setValue(tokenDataResponse.getValue());
        cameraDto.setTtl(tokenDataResponse.getTtl());
        return cameraDto;
    }
}
