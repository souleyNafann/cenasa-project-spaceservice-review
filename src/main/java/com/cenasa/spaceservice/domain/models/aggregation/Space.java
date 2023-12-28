package com.cenasa.spaceservice.domain.models.aggregation;


import com.cenasa.spaceservice.domain.models.entities.Reservation;
import com.cenasa.spaceservice.domain.models.entities.SpaceFiles;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data

@Table(name = "spaces")
public class Space {
    // UUId
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    private String name;

    private String description;


    private double price; // this a price for a space per day.

    private double capacity; // this is a capacity of a space.

    @Enumerated(EnumType.STRING)
    private Status status;

    // cover
    private String cover_path; // this is a path of a cover image.

    // location

    @CreatedDate
    private LocalDate createdAt; // this is a date of creation of a space.

    @LastModifiedDate
    private LocalDate updatedAt; // this is a date of last modification of a space.

    // has many files
    @OneToMany(mappedBy = "spaceId", cascade = CascadeType.ALL) // this is a list of files of a space.
    private List<SpaceFiles> files;

    @OneToMany(mappedBy = "spaceId", cascade = CascadeType.ALL) // this is a list of files of a space.
    private List<Reservation> reservations;

    public enum Status {

        ACTIVE,
        INACTIVE,
    }

    // constructor for full fields
    public Space(String name, String description, double price, double capacity, Status status, String cover_path, LocalDate createdAt, LocalDate updatedAt) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.capacity = capacity;
        this.status = status;
        this.cover_path = cover_path;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // constructor for zero fields
    public Space() {

    }
}

