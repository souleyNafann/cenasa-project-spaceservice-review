package com.cenasa.spaceservice.application.commandService.impl;


import com.cenasa.spaceservice.application.commandService.interfaces.IReservation;
import com.cenasa.spaceservice.application.exceptions.ConflictException;
import com.cenasa.spaceservice.application.exceptions.NotFoundException;
import com.cenasa.spaceservice.application.outboundService.interfaces.EmailService;
import com.cenasa.spaceservice.domain.models.commands.ReservationCommand;
import com.cenasa.spaceservice.domain.models.entities.Reservation;
import com.cenasa.spaceservice.domain.models.entities.ReservationSatus;
import com.cenasa.spaceservice.infrastructure.controllers.dto.ReservationDTO;
import com.cenasa.spaceservice.infrastructure.repositories.CalandarRepository;
import com.cenasa.spaceservice.infrastructure.repositories.ReservationRepository; // Assuming you have a repository like this

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.cenasa.spaceservice.infrastructure.repositories.SpaceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReservationService implements IReservation {

    private final ReservationRepository reservationRepository;
    private final SpaceRepository spaceRepository;

    private final CalandarRepository calandarRepository;
    private final ModelMapper modelMapper;

    private final EmailService emailService;

    // this is the constructor of the ReservationService
    public ReservationService(ReservationRepository reservationRepository, SpaceRepository spaceRepository, CalandarRepository calandarRepository, ModelMapper modelMapper, EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.spaceRepository = spaceRepository;
        this.calandarRepository = calandarRepository;

        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    // it's use for create the reservation
    @Override
    public ReservationDTO createReservation(ReservationCommand reservationCommand) {
        Reservation reservation = new Reservation();
        reservation.setSpace(spaceRepository.findById(reservationCommand.getSpaceId()).get());
        reservation.setStartDate(reservationCommand.getStartDate());
        reservation.setEndDate(reservationCommand.getEndDate());
        reservation.setStatus(ReservationSatus.ON_PENDING_FOR_ADMIN_APPROVAL);

        reservation.setReason(reservationCommand.getReason());
        // promotorName
        reservation.setPromotorName(reservationCommand.getPromotorName());
        // promotorEmail
        reservation.setPromotorEmail(reservationCommand.getPromotorEmail());
        // promotorPhone
        reservation.setPromotorPhone(reservationCommand.getPromotorPhone());
        reservationRepository.save(reservation);

        // create the calendar

        ReservationDTO reservationDTO = modelMapper.map(reservation, ReservationDTO.class);


        return reservationDTO;

    }


    // it's use for update the reservation by UUID
    @Override
    public ReservationDTO updateReservation(UUID id, String status) {
        Optional<Reservation> reservation = reservationRepository.findById(id);


        if (reservation.isPresent()) {
            updateReservationCalendar(reservation.get(), status);
            return modelMapper.map(reservation.get(), ReservationDTO.class);
        }
        throw new NotFoundException("Reservation not found");

    }


    // it's use for get all the reservation
    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations;
    }


    // it's use for delete the reservation by UUID
    @Override
    public void deleteReservation(String UUID) {
        // Delete the reservation by UUID
    }


    // it's use for get all the reservation by status
    @Override
    public Optional<Reservation> getReservationById(UUID id) {

        Object reservation = reservationRepository.findById(id);
        return Optional.of(modelMapper.map(reservation, Reservation.class));

    }


    // it's use for get all the reservation by space id
    @Override
    public List<ReservationDTO> getReservationBySpaceId(UUID id) {
        List<Reservation> reservations = reservationRepository.findAllBySpaceId(id);
        return reservations.stream().map(reservation -> modelMapper.map(reservation, ReservationDTO.class)).collect(Collectors.toList());
    }


    // it's use for update the status of the reservation
    public void updateReservationCalendar(Reservation reservation, String status) {
        LocalDateTime now = LocalDateTime.now();
        if (status.equals(ReservationSatus.ON_PENDING_FOR_ACCOUNTING_PAYMENT_APPROVAL.toString())) {
            reservation.setValidedAt(LocalDateTime.now());

            reservation.setStatus(ReservationSatus.ON_PENDING_FOR_ACCOUNTING_PAYMENT_APPROVAL);
            reservationRepository.save(reservation);
            emailService.sendEmailSpaceReservationConfirm(reservation);
            // mail must be sent here
            // generate bill
        }

        if (status.equals(ReservationSatus.ONE_PENDING_FOR_PUBLICATION_DATE.toString())) {
            if (now.isAfter(reservation.getValidedAt().plusDays(1))) {
                reservation.setStatus(ReservationSatus.REJECTED_FOR_NOT_PAYED);
                reservationRepository.save(reservation);
                // send mail here

                throw  new ConflictException("Reservation expiré");
            } else {
                reservation.setStatus(ReservationSatus.ONE_PENDING_FOR_PUBLICATION_DATE);
                reservationRepository.save(reservation);
                // send mail here


            }
        }

    }

    @Scheduled(fixedRate = 1000) // every 1 second
    public void scheduleFixedRateTask () {
    // get reservation when status is ON_PENDING_FOR_ACCOUNTING_PAYMENT_APPROVAL
        List<Reservation> reservations = reservationRepository.findAllByStatus(ReservationSatus.ON_PENDING_FOR_ACCOUNTING_PAYMENT_APPROVAL);
        for (Reservation reservation : reservations) {
            if (LocalDateTime.now().isAfter(reservation.getValidedAt().plusDays(1))) {
                reservation.setStatus(ReservationSatus.REJECTED_FOR_NOT_PAYED);
                reservation.setReason("Le delai de paiement est expiré");
                reservationRepository.save(reservation);
                // send mail here
            }
        }

        // get reservation when status is PUBLISHED
        List<Reservation> reservationsPublished = reservationRepository.findAllByStatusAndEndDateIsBefore(ReservationSatus.PUBLISHED, LocalDateTime.now());
        for (Reservation reservation : reservationsPublished) {
            reservation.setStatus(ReservationSatus.EXPIRED);
            reservationRepository.save(reservation);
            // send mail here
        }

    }

    // it's use for reject the reservation by UUID
    @Override
    public ReservationDTO rejectReservation(UUID id, String reason) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            reservation.get().setStatus(ReservationSatus.REJECTED_FOR_VIOLATION_OF_RULES);
            reservationRepository.save(reservation.get());
            reservation.get().setReason(reason);
            // send mail here

            return modelMapper.map(reservation.get(), ReservationDTO.class);


        }
        throw new NotFoundException("Reservation not found");
    }

}


