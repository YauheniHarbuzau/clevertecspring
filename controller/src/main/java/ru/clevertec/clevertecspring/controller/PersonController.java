package ru.clevertec.clevertecspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.clevertecspring.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.dto.response.PersonResponse;
import ru.clevertec.clevertecspring.entity.Person;
import ru.clevertec.clevertecspring.service.PersonService;
import ru.clevertec.clevertecspring.service.impl.PersonServiceImpl;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с {@link Person}
 *
 * @see PersonService
 * @see PersonServiceImpl
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
@Tag(name = "Person Controller", description = "Controller For Working With Persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/{uuid}")
    @Operation(summary = "Get Person", description = "Get Person By UUID")
    public ResponseEntity<PersonResponse> getByUuid(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(personService.getByUuid(uuid));
    }

    @GetMapping
    @Operation(summary = "Get Persons", description = "Get All Persons")
    public ResponseEntity<Page<PersonResponse>> getAll(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                                       @RequestParam(name = "pageMaxSize", defaultValue = "15") int pageMaxSize) {
        var pageable = PageRequest.of(pageNumber - 1, pageMaxSize);
        return ResponseEntity.ok(personService.findAll(pageable));
    }

    @GetMapping("/owners/{houseuuid}")
    @Operation(summary = "Get Owners", description = "Get House Owners By House UUID")
    public ResponseEntity<List<PersonResponse>> getHouseOwners(@PathVariable("houseuuid") UUID houseUuid) {
        return ResponseEntity.ok(personService.getHouseOwners(houseUuid));
    }

    @GetMapping("/owners/{houseuuid}/alltime")
    @Operation(summary = "Get Owners", description = "Get House Owners For All Time By House UUID")
    public ResponseEntity<List<PersonResponse>> getHouseOwnersAllTime(@PathVariable("houseuuid") UUID houseUuid) {
        return ResponseEntity.ok(personService.getHouseOwnersAllTime(houseUuid));
    }

    @GetMapping("/residents/{houseuuid}")
    @Operation(summary = "Get Residents", description = "Get House Residents By House UUID")
    public ResponseEntity<List<PersonResponse>> getHouseResidents(@PathVariable("houseuuid") UUID houseUuid) {
        return ResponseEntity.ok(personService.getHouseResidents(houseUuid));
    }

    @GetMapping("/residents/{houseuuid}/alltime")
    @Operation(summary = "Get Residents", description = "Get House Residents For All Time By House UUID")
    public ResponseEntity<List<PersonResponse>> getHouseResidentsAllTime(@PathVariable("houseuuid") UUID houseUuid) {
        return ResponseEntity.ok(personService.getHouseResidentsAllTime(houseUuid));
    }

    @GetMapping("/fulltextsearch/{text}")
    @Operation(summary = "Persons Full Text Search", description = "Full Text Search By Fields: name, surname")
    public ResponseEntity<List<PersonResponse>> fullTextSearch(@PathVariable("text") String text) {
        return ResponseEntity.ok(personService.fullTextSearch(text));
    }

    @PostMapping
    @Operation(summary = "Create Person", description = "Create New Person")
    public ResponseEntity<PersonResponse> create(@RequestBody @Valid PersonRequest personRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personService.create(personRequest));
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Update Person", description = "Update Previous Person")
    public ResponseEntity<PersonResponse> update(@PathVariable("uuid") UUID uuid,
                                                 @RequestBody @Valid PersonRequest personRequest) {
        return ResponseEntity.ok(personService.update(uuid, personRequest));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete Person", description = "Delete Person By UUID")
    public ResponseEntity<Void> deleteByUuid(@PathVariable("uuid") UUID uuid) {
        personService.deleteByUuid(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
