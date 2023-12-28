package com.cenasa.spaceservice.domain.models.aggregation.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor @Data
public class CreatingAudit {
    private String serviceName;
    private String objectCreated;
    private Date createDate;
    private String createBy;
    private String objectType;
    private String ipAdress;
}
