package com.github.reenatobruno.parts_api.controller;

import com.github.reenatobruno.parts_api.dto.PartRequestDTO;
import com.github.reenatobruno.parts_api.dto.PartResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Parts", description = "Endpoints for managing parts inventory")
public interface PartControllerOpenApi {

    @Operation(summary = "Create a new part", description = "Registers a new part in the database and returns the created resource along with its location URI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Part created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data or business rule violation")
    })
    ResponseEntity<PartResponseDTO> create(PartRequestDTO request);

    @Operation(summary = "List all parts with pagination", description = "Retrieves a paginated list of registered parts.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    ResponseEntity<Page<PartResponseDTO>> getAll(Pageable pageable);

    @Operation(summary = "Get a part by ID", description = "Retrieves specific part details based on the provided unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Part found successfully"),
            @ApiResponse(responseCode = "404", description = "Part not found")
    })
    ResponseEntity<PartResponseDTO> getById(Long id);

    @Operation(summary = "Update an existing part", description = "Updates all fields of an existing part based on its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Part updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided in request body"),
            @ApiResponse(responseCode = "404", description = "Part not found for update")
    })
    ResponseEntity<PartResponseDTO> update(Long id, PartRequestDTO request);

    @Operation(summary = "Delete a part by ID", description = "Permanently removes a part from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Part deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Part not found for deletion")
    })
    ResponseEntity<Void> delete(Long id);
}