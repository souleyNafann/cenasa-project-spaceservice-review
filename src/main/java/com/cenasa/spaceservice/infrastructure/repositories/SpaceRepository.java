package com.cenasa.spaceservice.infrastructure.repositories;


import com.cenasa.spaceservice.domain.models.aggregation.Space;
import com.cenasa.spaceservice.domain.models.entities.ReservationSatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SpaceRepository extends JpaRepository<Space, UUID> {

    // find all space winthout reservation and status is available
    List<Space> findAllByReservationsIsNullAndStatusEquals(Space.Status status);



    // find all space that have reservation and reservation Status in without status and status is available
    List<Space> findAllByReservationsIsNotNullAndReservations_StatusNotInAndStatusEquals(List<String> reservationSatus, Space.Status status);

    // find all spaces that have reservation and reservation end reservation startdate is after and reservation enddate is before and status is available
    List<Space> findAllByReservationsIsNotNullAndReservations_EndDateIsBeforeOrReservations_StartDateIsAfterAndStatusEquals(LocalDateTime startDate, LocalDateTime endDate, Space.Status status);

}

