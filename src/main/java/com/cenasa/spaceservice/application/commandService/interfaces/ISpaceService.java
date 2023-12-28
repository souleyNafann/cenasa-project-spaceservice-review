package com.cenasa.spaceservice.application.commandService.interfaces;

import com.cenasa.spaceservice.domain.models.aggregation.Space;
import com.cenasa.spaceservice.domain.models.commands.SpaceCommand;
import com.cenasa.spaceservice.infrastructure.controllers.dto.SpaceDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ISpaceService {

    // Method to create a new space
    SpaceDTO createSpace(SpaceCommand spaceCommand);

    // Method to update an existing space
    SpaceDTO updateSpace(UUID id, SpaceCommand spaceCommand);

    // Method to retrieve a space by its ID
    Optional<SpaceDTO> getSpaceById(UUID id);

    // Method to retrieve all spaces
    List<SpaceDTO> getAllSpaces();

    // Method to delete a space by its ID
    void deleteSpace(UUID id);
}
