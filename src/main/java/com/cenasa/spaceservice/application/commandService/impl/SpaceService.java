package com.cenasa.spaceservice.application.commandService.impl;

import com.cenasa.spaceservice.application.commandService.interfaces.ISpaceService;
import com.cenasa.spaceservice.application.exceptions.NotFoundException;
import com.cenasa.spaceservice.domain.models.aggregation.Space;
import com.cenasa.spaceservice.domain.models.commands.SpaceCommand;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.cenasa.spaceservice.domain.models.entities.ReservationSatus;
import com.cenasa.spaceservice.infrastructure.controllers.dto.SpaceDTO;
import com.cenasa.spaceservice.infrastructure.repositories.SpaceRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Data
@Service
public class SpaceService implements ISpaceService {

    private final SpaceRepository spaceRepository;


    private final ModelMapper modelMapper;

    @Override
    public SpaceDTO createSpace(SpaceCommand spaceCommand) {

        Space space = new Space();
        space.setName(spaceCommand.getName());
        space.setStatus(Space.Status.ACTIVE);
        space.setCapacity(spaceCommand.getCapacity());
        space.setPrice(spaceCommand.getPrice());
        space.setDescription(spaceCommand.getDescription());
        spaceRepository.save(space);

        return modelMapper.map(space, SpaceDTO.class);
    }

    @Override
    public SpaceDTO updateSpace(UUID id, SpaceCommand spaceCommand) {

        Optional<Space> space = spaceRepository.findById(id);
        if (space.isPresent()) {
            space.get().setName(spaceCommand.getName());
            space.get().setCapacity(spaceCommand.getCapacity());
            space.get().setPrice(spaceCommand.getPrice());
            space.get().setDescription(spaceCommand.getDescription());
            spaceRepository.save(space.get());
            return modelMapper.map(space.get(), SpaceDTO.class);
        }
        throw new NotFoundException("Space not found");

    }

    @Override
    public Optional<SpaceDTO> getSpaceById(UUID id) {

        Optional<Space> space = spaceRepository.findById(id);
        return Optional.of(modelMapper.map(space, SpaceDTO.class));
    }

    @Override
    public List<SpaceDTO> getAllSpaces() {

        List<Space> spaces = spaceRepository.findAll();
        return modelMapper.map(spaces, List.class);


    }

    @Override
    public void deleteSpace(UUID id) {

            Optional<Space> space = spaceRepository.findById(id);
            if (space.isPresent())
                spaceRepository.delete(space.get());
            else
                throw new NotFoundException("Space not found");
    }


    public List<Space> getAllSpacesAvailbleSpace(LocalDateTime startDate, LocalDateTime endDate) {
        List<String> reservationSatus = new ArrayList<>();
        reservationSatus.add(ReservationSatus.PUBLISHED.toString());
        reservationSatus.add(ReservationSatus.ON_PENDING_FOR_ACCOUNTING_PAYMENT_APPROVAL.toString());
        List<Space> spacesWithoutDateConflit = spaceRepository.findAllByReservationsIsNotNullAndReservations_EndDateIsBeforeOrReservations_StartDateIsAfterAndStatusEquals(
                startDate, endDate, Space.Status.ACTIVE);
        List<Space> spacesWithoutReservation = spaceRepository.findAllByReservationsIsNullAndStatusEquals(Space.Status.ACTIVE);
        List<Space> spacesWithoutReservationAndStatus = spaceRepository.findAllByReservationsIsNotNullAndReservations_StatusNotInAndStatusEquals(reservationSatus, Space.Status.ACTIVE);
          List<Space> spaces = new ArrayList<>();
        spaces.addAll(spacesWithoutDateConflit);
        spaces.addAll(spacesWithoutReservation);
        spaces.addAll(spacesWithoutReservationAndStatus);
        return spaces;

    }
}
