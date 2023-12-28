package com.cenasa.spaceservice.domain.models.entities;


import com.cenasa.spaceservice.domain.models.aggregation.Space;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Reservation {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(generator = "uuid2")
    private UUID id;

    private UUID spaceId;

    private String promotorName;

    private String promotorEmail;

    private String promotorPhone;

    private String reason;

    @Enumerated(EnumType.STRING)
    private ReservationSatus status;

    private LocalDateTime startDate;

    private LocalDateTime endDate; // example: can be 2021-12-31 23:59:59

    private LocalDateTime createdAt;

    private LocalDateTime validedAt;

    private String rejectReason;

    // space relation
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spaceId", insertable = false, updatable = false)
    private Space space;



    // calendar relation





    public Reservation() {

    }

    // constructor for full reservation
    public Reservation(UUID spaceId, String promotorName, String promotorEmail, String promotorPhone, String reason, ReservationSatus status, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime createdAt, LocalDateTime validedAt) {
        this.spaceId = spaceId;
        this.promotorName = promotorName;
        this.promotorEmail = promotorEmail;
        this.promotorPhone = promotorPhone;
        this.reason = reason;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.validedAt = validedAt;
    }
}
