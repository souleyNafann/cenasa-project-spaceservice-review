package com.cenasa.spaceservice.domain.models.entities;

public enum ReservationSatus {

    EXPIRED, // reservation is expired
    PUBLISHED, // reservation is published
    ON_PENDING_FOR_ADMIN_APPROVAL, // reservation is on pending for admin approval

    ON_PENDING_FOR_ACCOUNTING_PAYMENT_APPROVAL, // reservation is on pending for accounting payment approval

    ONE_PENDING_FOR_PUBLICATION_DATE, // reservation is on pending for publication date


    REJECTED_FOR_VIOLATION_OF_RULES, // reservation is rejected for violation of rules

    REJECTED_FOR_NOT_PAYED, // reservation is rejected for not payed
}

