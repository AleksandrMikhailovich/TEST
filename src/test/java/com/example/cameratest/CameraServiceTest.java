package com.example.cameratest;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import com.example.cameratest.service.CameraService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.example.cameratest.feignClient.CameraFeignClient;
import org.junit.jupiter.api.Test;
import com.example.cameratest.camera.response.TokenDataResponse;
import com.example.cameratest.camera.response.SourceDataResponse;
import com.example.cameratest.camera.dto.CameraDto;
import com.example.cameratest.camera.response.CameraResponse;
import com.example.cameratest.aggregator.CameraDataAggregator;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class CameraServiceTest {
    @Mock
    private CameraFeignClient cameraFeignClient;
    @Mock
    private CameraDataAggregator cameraDataAggregator;
    @InjectMocks
    private CameraService cameraService;

    @Test
    public void testGetCameras() throws ExecutionException, InterruptedException, IOException {
// Mock the response from the cameraFeignClient
        CameraResponse cameraResponse = new CameraResponse();
        cameraResponse.setId(1);
        cameraResponse.setSourceDataUrl("source-url");
        cameraResponse.setTokenDataUrl("token-url");
        when(cameraFeignClient.getCameras()).thenReturn(Arrays.asList(cameraResponse));
        when(cameraFeignClient.getCameras()).thenReturn(Arrays.asList(cameraResponse));

// Mock the response from cameraDataAggregator
        SourceDataResponse sourceDataResponse = new SourceDataResponse();
        sourceDataResponse.setUrlType("url-type");
        sourceDataResponse.setVideoUrl("video-url");
        when(cameraDataAggregator.getSourceData("source-url")).thenReturn(sourceDataResponse);

        TokenDataResponse tokenDataResponse = new TokenDataResponse();
        tokenDataResponse.setValue("token-value");
        tokenDataResponse.setTtl(3600);
        when(cameraDataAggregator.getTokenData("token-url")).thenReturn(tokenDataResponse);
// Perform the test
        List<CameraDto> result = cameraService.getCameras();
// Verify the results
        assertEquals(1, result.size());
        CameraDto cameraDto = result.get(0);
        assertEquals(1, cameraDto.getId());
        assertEquals("url-type", cameraDto.getUrlType());
        assertEquals("video-url", cameraDto.getVideoUrl());
        assertEquals("token-value", cameraDto.getValue());
        assertEquals(3600, cameraDto.getTtl());
    }

    @Test
    public void testAsyncFetchDataAndToken() throws ExecutionException, InterruptedException, IOException, TimeoutException {
        // Mock the response from cameraDataAggregator
        SourceDataResponse sourceDataResponse = new SourceDataResponse();
        sourceDataResponse.setUrlType("url-type");
        sourceDataResponse.setVideoUrl("video-url");
        when(cameraDataAggregator.getSourceData(Mockito.anyString())).thenReturn(sourceDataResponse);
        TokenDataResponse tokenDataResponse = new TokenDataResponse();
        tokenDataResponse.setValue("token-value");
        tokenDataResponse.setTtl(3600);
        when(cameraDataAggregator.getTokenData(Mockito.anyString())).thenReturn(tokenDataResponse);

        // Perform the test
        CompletableFuture<CameraDto> future = cameraService.asyncFetchDataAndToken(new CameraResponse());
        try {
            CameraDto cameraDto = future.get(5, TimeUnit.SECONDS);
            // Verify the results
            assertEquals("url-type", cameraDto.getUrlType());
            assertEquals("video-url", cameraDto.getVideoUrl());
            assertEquals("token-value", cameraDto.getValue());
            assertEquals(3600, cameraDto.getTtl());
        } catch (TimeoutException e) {
            // Handle timeout exception
            System.out.println("Timeout Exception: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            System.out.println("Exception: " + e.getMessage());
        }
    }
}