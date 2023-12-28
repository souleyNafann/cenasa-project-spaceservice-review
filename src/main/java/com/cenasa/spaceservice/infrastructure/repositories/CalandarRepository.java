package com.cenasa.spaceservice.infrastructure.repositories;

import com.cenasa.spaceservice.domain.models.entities.Calendar;
import com.cenasa.spaceservice.domain.models.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface CalandarRepository extends JpaRepository<Calendar, UUID> {

    List<Calendar> findAllByEventAndEndDateBefore(Event event, LocalDateTime endDate);

    List<Calendar> findAllByEventAndStartDateBeforeAndEndDateAfter(Event event, LocalDateTime startDate, LocalDateTime endDate);
}