package com.cenasa.spaceservice.domain.models.aggregation.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor @Data
public class DeletingAudit {
    private String serviceName;
    private String objectDeleted;
    private Date deleteDate;
    private String deleteBy;
    private String objectType;
    private String ipAdress;
}
