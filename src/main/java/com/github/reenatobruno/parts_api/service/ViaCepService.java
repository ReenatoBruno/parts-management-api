package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.ViaCepResponseDTO;
import com.github.reenatobruno.parts_api.exception.ExternalServiceException;
import com.github.reenatobruno.parts_api.exception.ZipNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Slf4j
@Service
public class ViaCepService {

    private final RestClient restClient;

    public ViaCepService(
            @Value("${client.viacep.url:https://viacep.com.br/ws}") String baseUrl,
            @Value("${client.viacep.timeout.connect:5}") long connectTimeout,
            @Value("${client.viacep.timeout.read:10}") long readTimeout
    ) {

        HttpClient javaHttpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(connectTimeout))
                .build();

        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(javaHttpClient);
        factory.setReadTimeout(Duration.ofSeconds(readTimeout));

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }

    @Retryable(
            retryFor = {ExternalServiceException.class, ResourceAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )

    public ViaCepResponseDTO findByZip(String zip) {

        ViaCepResponseDTO responseDTO = restClient.get()
                .uri("/{zip}/json", zip)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new ZipNotFoundException(zip);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new ExternalServiceException("ViaCep Api unavailable");
                })
                .body(ViaCepResponseDTO.class);

        if (responseDTO == null || "true".equals(responseDTO.erro())) {
            throw new ZipNotFoundException(zip);
        }
        return responseDTO;
    }
}
