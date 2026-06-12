package com.github.reenatobruno.parts_api.controller;

import com.github.reenatobruno.parts_api.dto.PartRequestDTO;
import com.github.reenatobruno.parts_api.dto.PartResponseDTO;
import com.github.reenatobruno.parts_api.dto.PartUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Part Controller", description = "Endpoints for API managing parts")
public interface PartControllerOpenApi {

    @Operation(
            summary = "Create a new part",
            description = "Creates and persists a new part, returning the created resource and its corresponding location URI."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Part created successfully",
                            content = @Content(
                                    schema = @Schema(implementation = PartResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Part number already exists",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error")
            }
    )
    ResponseEntity<PartResponseDTO> create(@Valid @RequestBody PartRequestDTO request);

    @Operation(
            summary = "Get a part by ID",
            description = "Retrieves specific part details based on the provided unique identifier"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Part found successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Part not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error")
            }
    )
    ResponseEntity<PartResponseDTO> getById(@Parameter(description = "Part ID")@PathVariable Long id);

    @Operation(
            summary = "List all parts with pagination",
            description = "Retrieves a paginated list of registered parts"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List retrieved successfully"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error")
            }
    )
    ResponseEntity<Page<PartResponseDTO>> getAll(
            @Parameter(description = "Filter by part name") @RequestParam(required = false) String partName,
            @Parameter(description = "Pagination parameters (page, size, sort)") Pageable pageable);

    @Operation(
            summary = "Update a part by ID",
            description = "Updates an existing part's data except for the part number"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Part updated successfully",
                            content = @Content(
                                    schema = @Schema(implementation = PartResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request data",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Part not found",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content
                    )
            }
    )
    ResponseEntity<PartResponseDTO> update(@Parameter(description = "ID of the part to be updated") @PathVariable Long id, @Valid @RequestBody PartUpdateDTO request);

    @Operation(
            summary = "Delete a part by ID",
            description = "Permanently removes a part from the database"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Part deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Part not found for deletion"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error")
            }
    )
    ResponseEntity<Void> delete(@Parameter(description = "ID of the part to be deleted")@PathVariable Long id);
}
