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
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.service.HouseService;
import ru.clevertec.clevertecspring.service.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.service.dto.response.HouseResponse;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с {@link House}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/houses")
public class HouseController {

    private final HouseService service;

    @GetMapping("/{uuid}")
    public ResponseEntity<HouseResponse> getByUuid(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(service.getByUuid(uuid));
    }

    @GetMapping
    public ResponseEntity<List<HouseResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<HouseResponse> create(@RequestBody @Valid HouseRequest houseRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(houseRequest));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<HouseResponse> update(@PathVariable("uuid") UUID uuid,
                                                @RequestBody @Valid HouseRequest houseRequest) {
        return ResponseEntity.ok(service.update(uuid, houseRequest));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteByUuid(@PathVariable("uuid") UUID uuid) {
        service.deleteByUuid(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/fulltextsearch/{text}")
    public ResponseEntity<List<HouseResponse>> fullTextSearch(@PathVariable("text") String text) {
        return ResponseEntity.ok(service.fullTextSearch(text));
    }
}
