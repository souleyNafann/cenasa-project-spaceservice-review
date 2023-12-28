package com.cenasa.spaceservice.domain.models.commands;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SpaceCommand {

    @NotNull(message = "Le nom de l'espace est obligatoire")
    private String name;

    private String description;

    @NotNull(message = "Le prix de l'espace est obligatoire")
    @Min(value = 0, message = "Le prix de l'espace doit être supérieur à 0")
    private Double price;

    private Double capacity;

    private  Double surface;

    // Standard getters and setters
}
