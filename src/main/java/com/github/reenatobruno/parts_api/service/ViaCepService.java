package com.github.reenatobruno.parts_api.service;

import com.github.reenatobruno.parts_api.dto.ViaCepResponseDTO;
import com.github.reenatobruno.parts_api.exception.ZipNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ViaCepService {

    private final RestClient restClient;

    public ViaCepService() {
        this.restClient = RestClient.builder()
                .baseUrl("https://viacep.com.br/ws")
                .build();
    }

    public ViaCepResponseDTO findByZip(String zip) {

        ViaCepResponseDTO responseDTO = restClient.get()
                .uri("/{zip}/json", zip)
                .retrieve()
                .body(ViaCepResponseDTO.class);

        if (responseDTO == null || "true".equals(responseDTO.erro())) {
            throw new ZipNotFoundException(zip);
        }
        return responseDTO;
    }
}
