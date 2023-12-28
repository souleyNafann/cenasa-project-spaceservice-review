package com.cenasa.spaceservice.domain.models.commands;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ReservationCommand {
    @NotNull(message = "L'espace est obligatoire")
    private UUID spaceId;

    private String promotorName;
    @NotNull(message = "L'email du promoteur est obligatoire")
    private String promotorEmail;

    @NotNull(message = "Le numéro de téléphone du promoteur est obligatoire")
    private String promotorPhone;

    @NotNull(message = "La raison de la réservation est obligatoire")
    private String reason;

    @NotNull(message = "La date de début de la réservation est obligatoire")
    private LocalDateTime startDate;

    @NotNull(message = "La date de fin de la réservation est obligatoire")
    private LocalDateTime endDate;








}
