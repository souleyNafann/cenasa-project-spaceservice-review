package com.cenasa.spaceservice.infrastructure.controllers.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ReservationDTO {
    private UUID id;

    private SpaceDTO space;

    private String promotorName;

    private String promotorEmail;

    private String promotorPhone;
    
    private String reason;

    private String status;

    private String startDate;

    private String endDate;

    private SpaceDTO spaceDTO;

    private LocalDateTime createdAt;

    private LocalDateTime validedAt;



}
