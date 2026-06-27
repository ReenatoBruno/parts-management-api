package com.github.reenatobruno.parts_api.controller;

import com.github.reenatobruno.parts_api.dto.UserChangePasswordDTO;
import com.github.reenatobruno.parts_api.dto.UserRequestDTO;
import com.github.reenatobruno.parts_api.dto.UserResponseDTO;
import com.github.reenatobruno.parts_api.dto.UserUpdateDTO;
import com.github.reenatobruno.parts_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAll(@RequestParam(required = false) String userName, @PageableDefault(size = 20, sort = "userName") Pageable pageable) {
        return ResponseEntity.ok(service.getAll(userName, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody UserUpdateDTO updateDTO) {
        return ResponseEntity.ok(service.update(id, updateDTO));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable  UUID id, @Valid @RequestBody UserChangePasswordDTO passwordDTO) {
        return ResponseEntity.noContent().build();
    }

}
