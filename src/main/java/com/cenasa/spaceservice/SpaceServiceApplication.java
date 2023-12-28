package com.cenasa.spaceservice;

import com.cenasa.spaceservice.domain.models.aggregation.Space;
import com.cenasa.spaceservice.domain.models.entities.Calendar;
import com.cenasa.spaceservice.domain.models.entities.Event;
import com.cenasa.spaceservice.domain.models.entities.Reservation;
import com.cenasa.spaceservice.domain.models.entities.ReservationSatus;
import com.cenasa.spaceservice.infrastructure.repositories.CalandarRepository;
import com.cenasa.spaceservice.infrastructure.repositories.ReservationRepository;
import com.cenasa.spaceservice.infrastructure.repositories.SpaceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@SpringBootApplication
@EnableScheduling
public class SpaceServiceApplication implements CommandLineRunner {
	private SpaceRepository spaceRepository;
	private ReservationRepository reservationRepository;
	private CalandarRepository calandarRepository;

	public SpaceServiceApplication(SpaceRepository spaceRepository, ReservationRepository reservationRepository, CalandarRepository calandarRepository) {
		this.spaceRepository = spaceRepository;
		this.reservationRepository = reservationRepository;
		this.calandarRepository = calandarRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(SpaceServiceApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		// create space for testing
		Space space = new Space();
		space.setName("Space 1");
		space.setCapacity(10);
		space.setPrice(100);
		space.setDescription("Description for space 1");
		space.setStatus(Space.Status.ACTIVE);
		space = spaceRepository.save(space);



		Reservation reservation = new Reservation();

		reservation.setSpaceId(space.getId());
		reservation.setStartDate(LocalDateTime.now().minusDays(1));  // it's been started yesterday and finish after 3 days
		reservation.setEndDate(LocalDateTime.now().plusDays(3));
		reservation.setStatus(ReservationSatus.PUBLISHED);
		reservation.setReason("Reason for reservation 1");
		reservation.setId(UUID.randomUUID());
		reservation = reservationRepository.save(reservation);
		// space 2
		Space space2 = new Space();
		space2.setName("Space 2");
		space2.setCapacity(10);
		space2.setPrice(100);
		space2.setDescription("Description for space 2");
		space2.setStatus(Space.Status.ACTIVE);
		space2 = spaceRepository.save(space2);

		Reservation reservation2 = new Reservation();
		reservation2.setSpaceId(space2.getId());
		reservation2.setStartDate(LocalDateTime.now());  // it's been started yesterday and finish after 3 days
		reservation2.setStatus(ReservationSatus.PUBLISHED);
		reservation2.setEndDate(LocalDateTime.now().plusMinutes(3));
		reservation2.setReason("Reason for reservation 2");
		reservation2.setId(UUID.randomUUID());
		reservation2 = reservationRepository.save(reservation2);
		// space 3

		System.out.println("--------------------------------------------");
		System.out.println(".::: Seeders are executed successfully :::.");
		System.out.println("--------------------------------------------");



	}
}
