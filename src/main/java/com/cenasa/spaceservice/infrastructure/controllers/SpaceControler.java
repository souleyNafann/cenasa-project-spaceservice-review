package com.cenasa.spaceservice.infrastructure.controllers;


import com.cenasa.spaceservice.application.commandService.impl.SpaceService;
import com.cenasa.spaceservice.domain.models.aggregation.Space;
import com.cenasa.spaceservice.domain.models.commands.SpaceCommand;
import com.cenasa.spaceservice.infrastructure.controllers.dto.SpaceDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/spaces")
@AllArgsConstructor
@Configuration
public class SpaceControler {

    private final SpaceService spaceService;



    @GetMapping
    public ResponseEntity<List<SpaceDTO>> getAllSpaces() {

        List spaceDTOList = spaceService.getAllSpaces();

        return ResponseEntity.ok().body(spaceDTOList);
    }

    @PostMapping
    public ResponseEntity<SpaceDTO> createSpace(@Valid @RequestBody SpaceCommand spaceCommand) {

        SpaceDTO spaceDTO = spaceService.createSpace(spaceCommand);

        return ResponseEntity.ok().body(spaceDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<SpaceDTO>> getSpaceById(@PathVariable(value = "id") UUID spaceId) {

        Optional<SpaceDTO> spaceDTO = spaceService.getSpaceById(spaceId);

        return ResponseEntity.ok().body(spaceDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpaceDTO> updateSpace(@PathVariable(value = "id") UUID spaceId,
                                                @Valid @RequestBody SpaceCommand spaceCommand) {

        SpaceDTO spaceDTO = spaceService.updateSpace(spaceId, spaceCommand);

        return ResponseEntity.ok().body(spaceDTO);
    }

    // delete space
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpace(@PathVariable(value = "id") UUID spaceId) {

        spaceService.deleteSpace(spaceId);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/available/{startDate}/{endDate}")
    public ResponseEntity<List<Space>> getAllSpacesAvailbleSpace(@PathVariable(value = "startDate") String startDate,
                                                                 @PathVariable(value = "endDate") String endDate) {

        List<Space> spaceDTOList = spaceService.getAllSpacesAvailbleSpace(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate));

        return ResponseEntity.ok().body(spaceDTOList);
    }

}
