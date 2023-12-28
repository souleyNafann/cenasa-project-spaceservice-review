package com.cenasa.spaceservice.application.commandService.interfaces;

import com.cenasa.spaceservice.domain.models.commands.ReservationCommand;
import com.cenasa.spaceservice.domain.models.entities.Reservation;
import com.cenasa.spaceservice.infrastructure.controllers.dto.ReservationDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IReservation {

    ReservationDTO createReservation(ReservationCommand reservationCommand);

    ReservationDTO updateReservation(UUID UUID, String status);


    List<Reservation> getAllReservations();

    void deleteReservation(String UUID);



    Optional<Reservation> getReservationById(UUID id);


    List<ReservationDTO> getReservationBySpaceId(UUID id);

    // it's use for reject the reservation by UUID
    ReservationDTO rejectReservation(UUID id, String reason);
}