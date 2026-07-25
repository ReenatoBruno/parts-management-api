package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.ViaCepResponseDTO;
import com.github.reenatobruno.parts_api.exception.ViaCepExternalServiceException;
import com.github.reenatobruno.parts_api.exception.ZipNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

public class ViaCepServicee {

    private final RestClient restClient;

    public ViaCepServicee(
            @Value("${client.viacep:https://viacep.com.br/ws}") String baseUrl,
            @Value("${client.viacep.timeout.connect:5") long connectTimeout,
            @Value("${client.viacep.timeout.read:10}") long readTimeout
    )
    {
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

    public ViaCepResponseDTO findByZip(String zip) {

        ViaCepResponseDTO responseDTO = restClient.get()
                .uri("/zip/json", zip)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new ZipNotFoundException(zip);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new ViaCepExternalServiceException("ViaCep API unavailable");
                })
                .body(ViaCepResponseDTO.class);

        if (responseDTO == null || "true".equals(responseDTO.erro())) {
            throw new ZipNotFoundException(zip);
        }
        return responseDTO;
    }
}
