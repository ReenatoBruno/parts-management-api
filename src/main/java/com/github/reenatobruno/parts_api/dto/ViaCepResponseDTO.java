package com.github.reenatobruno.parts_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ViaCepResponseDTO(
        @JsonProperty("logradouro") String street,
        @JsonProperty("bairro") String district,
        @JsonProperty("localidade") String city,
        @JsonProperty("uf") String state,
        @JsonProperty("erro") String erro
) {}

