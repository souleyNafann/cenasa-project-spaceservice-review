package com.cenasa.spaceservice.infrastructure.controllers.dto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Data
public class SpaceDTO {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private double capacity;
    private String status;
    private String coverPath;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private List<SpaceFileDTO> files; // Assuming SpaceFileDTO is a DTO for SpaceFiles

    // Standard getters and setters
}
