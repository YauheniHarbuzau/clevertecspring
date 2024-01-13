package ru.clevertec.clevertecspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.clevertecspring.dao.entity.Person;
import ru.clevertec.clevertecspring.service.PersonService;
import ru.clevertec.clevertecspring.service.dto.request.PersonRequest;
import ru.clevertec.clevertecspring.service.dto.response.PersonResponse;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с {@link Person}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final PersonService service;

    @GetMapping("/{uuid}")
    public ResponseEntity<PersonResponse> getByUuid(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(service.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<PersonResponse> create(@RequestBody @Valid PersonRequest personRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(personRequest));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PersonResponse> update(@PathVariable("uuid") UUID uuid,
                                                 @RequestBody @Valid PersonRequest personRequest) {
        return ResponseEntity.ok(service.update(uuid, personRequest));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable("uuid") UUID uuid) {
        service.deleteByUuid(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/residents/{houseuuid}")
    public ResponseEntity<List<PersonResponse>> getHouseResidents(@PathVariable("houseuuid") UUID houseUuid) {
        return ResponseEntity.ok(service.getHouseResidents(houseUuid));
    }


    @GetMapping("/fulltextsearch/{text}")
    public ResponseEntity<List<PersonResponse>> fullTextSearch(@PathVariable("text") String text) {
        return ResponseEntity.ok(service.fullTextSearch(text));
    }
}
