package com.cenasa.spaceservice.domain.models.entities;


import com.cenasa.spaceservice.domain.models.aggregation.Space;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import java.util.UUID;

@Data
@Entity
public class SpaceFiles {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID spacefilesId;

    private String path;

    private UUID spaceId;

}
