package com.cenasa.spaceservice.infrastructure.repositories;

import com.cenasa.spaceservice.domain.models.entities.Reservation;
import com.cenasa.spaceservice.domain.models.entities.ReservationSatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {



    void deleteById(UUID reservationId);

    // reservacion by spaceId

    List<Reservation> findAllBySpaceId(UUID spaceId);
    // find reservation status
    List<Reservation> findAllByStatus(ReservationSatus status);

    List<Reservation> findAllByStatusAndEndDateIsBefore(ReservationSatus status, LocalDateTime endDate);
}
