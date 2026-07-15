package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.ViaCepResponseDTO;
import com.github.reenatobruno.parts_api.exception.ExternalServiceException;
import com.github.reenatobruno.parts_api.exception.ZipNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Slf4j
@Service
public class ViaCepService {

    private final RestClient restClient;

    public ViaCepService() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(Duration.ofSeconds(5));
        factory.setReadTimeout(Duration.ofSeconds(10));

        this.restClient = RestClient.builder()
                .baseUrl("https://viacep.com.br/ws")
                .requestFactory(factory)
                .build();
    }

    public ViaCepResponseDTO findByZip(String zip) {

        try {
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

        } catch (ZipNotFoundException | ExternalServiceException e) {
            throw e;
        } catch (Exception e) {

            log.error("Error fetching zip from ViaCep: {}", zip, e);

            throw new ExternalServiceException("Error consulting zip: " + zip);
        }
    }
}
