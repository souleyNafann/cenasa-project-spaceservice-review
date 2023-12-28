package com.cenasa.spaceservice.domain.models.aggregation.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor @Data
public class UpdatingAudit {
    private String serviceName;
    private String oldObject;
    private String newObject;
    private Date updateDate;
    private String updateBy;
    private String objectType;
    private String ipAdress;
}
