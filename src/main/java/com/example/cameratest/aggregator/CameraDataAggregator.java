package com.example.cameratest.aggregator;

import com.example.cameratest.camera.response.SourceDataResponse;
import com.example.cameratest.camera.response.TokenDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Slf4j
public class CameraDataAggregator {
    private final ObjectMapper objectMapper;

    public CameraDataAggregator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public SourceDataResponse getSourceData(String sourceDataUrl) throws IOException {
        try {
            String json = fetchDataFromUrl(sourceDataUrl);
            return objectMapper.readValue(json, SourceDataResponse.class);
        } catch (IOException e) {
            log.error("Ошибка при получении исходных данных", sourceDataUrl, e.getMessage(), e);
            throw e; // Пробросим исключение дальше для обработки на уровне выше
        }
    }

    public TokenDataResponse getTokenData(String tokenDataUrl) throws IOException {
        try {
            String json = fetchDataFromUrl(tokenDataUrl);
            return objectMapper.readValue(json, TokenDataResponse.class);
        } catch (IOException e) {
            log.error("Ошибка при получении исходных данных", tokenDataUrl, e.getMessage(), e);
            throw e; // Пробросим исключение дальше для обработки на уровне выше
        }
    }

    private static String fetchDataFromUrl(String url) throws IOException {
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

        try {
            StringBuilder content = new StringBuilder();
            while (connection.getInputStream().available() > 0) {
                content.append((char) connection.getInputStream().read());
            }
            return content.toString();
        } finally {
            connection.disconnect();
        }
    }
}