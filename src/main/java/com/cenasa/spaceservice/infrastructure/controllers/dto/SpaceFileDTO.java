package com.cenasa.spaceservice.infrastructure.controllers.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SpaceFileDTO {
    // Assuming what fields SpaceFiles has
    private UUID id;
    private String filePath;
    // Other relevant fields

    // Standard getters and setters
}
