package com.cenasa.spaceservice.application.outboundService.interfaces;


import com.cenasa.spaceservice.domain.models.entities.Reservation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "email-service")
public interface EmailService {

    @PostMapping("/space-reservation/submitted")
    void sendEmailSpaceReservationConfirm(Reservation reservation);

    @PostMapping("/space-reservation/approved")
    void sendEmailSpaceReservationForPaiment(Reservation reservation);

}
