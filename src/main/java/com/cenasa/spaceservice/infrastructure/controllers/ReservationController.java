package com.cenasa.spaceservice.infrastructure.controllers;


import com.cenasa.spaceservice.application.commandService.impl.ReservationService;
import com.cenasa.spaceservice.domain.models.commands.ReservationCommand;
import com.cenasa.spaceservice.domain.models.entities.Reservation;
import com.cenasa.spaceservice.domain.models.entities.ReservationSatus;
import com.cenasa.spaceservice.infrastructure.controllers.dto.ReservationDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/reservations-service")
@AllArgsConstructor
@Configuration
public class ReservationController {

        private final ReservationService reservationService;


        @GetMapping
        public ResponseEntity<List<ReservationDTO>> getAllReservations() {

            List reservationDTOList = reservationService.getAllReservations();

            return ResponseEntity.ok().body(reservationDTOList);
        }

        @PostMapping
        public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationCommand reservationCommand) {

            ReservationDTO reservationDTO = reservationService.createReservation(reservationCommand);

            return ResponseEntity.ok().body(reservationDTO);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Optional<Reservation>> getReservationById(@PathVariable(value = "id") UUID reservationId) {

            Optional<Reservation> reservationDTO = reservationService.getReservationById(reservationId);

            return ResponseEntity.ok().body(reservationDTO);
        }

        @PutMapping("/{id}/status/{status}")
        public ResponseEntity<ReservationDTO> updateReservation(@PathVariable(value = "id") UUID reservationId,
                                                                @PathVariable(value = "status") String status) {
            switch (status) {
                case "APPROVED":
                    status = ReservationSatus.ON_PENDING_FOR_ACCOUNTING_PAYMENT_APPROVAL.toString();
                    break;
                case "PAYED":
                    status = ReservationSatus.ON_PENDING_FOR_ADMIN_APPROVAL.toString();
                    break;
                default:
                    return ResponseEntity.badRequest().build();
            }
            ReservationDTO reservationDTO = reservationService.updateReservation(reservationId, status);
            return ResponseEntity.ok().body(reservationDTO);
        }

        @PutMapping("/{id}/reject") // this is for reject the reservation the reason must be in the body
        public ResponseEntity<ReservationDTO> rejectReservation(@PathVariable(value = "id") UUID reservationId,

                                                                @Valid @RequestBody String reason) {
            ReservationDTO reservationDTO = reservationService.rejectReservation(reservationId, reason);
            return ResponseEntity.ok().body(reservationDTO);
        }

        //get status of the reservation by UUID this is for the client
        @GetMapping("/{id}/status")
        public ResponseEntity<ReservationSatus> getStatus(@PathVariable(value = "id") UUID reservationId) {
            ReservationSatus reservationSatus = reservationService.getStatus(reservationId);
            return ResponseEntity.ok().body(reservationSatus);
        }


}
