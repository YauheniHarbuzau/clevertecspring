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
import ru.clevertec.clevertecspring.dao.entity.House;
import ru.clevertec.clevertecspring.service.HouseService;
import ru.clevertec.clevertecspring.service.dto.request.HouseRequest;
import ru.clevertec.clevertecspring.service.dto.response.HouseResponse;
import ru.clevertec.clevertecspring.service.impl.HouseServiceImpl;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с {@link House}
 *
 * @see HouseService
 * @see HouseServiceImpl
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/houses")
@Tag(name = "House Controller", description = "Controller For Working With Houses")
public class HouseController {

    private final HouseService houseService;

    @GetMapping("/{uuid}")
    @Operation(summary = "Get House", description = "Get House By UUID")
    public ResponseEntity<HouseResponse> getByUuid(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.ok(houseService.getByUuid(uuid));
    }

    @GetMapping
    @Operation(summary = "Get Houses", description = "Get All Houses")
    public ResponseEntity<Page<HouseResponse>> getAll(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                                                      @RequestParam(name = "pageMaxSize", defaultValue = "15") int pageMaxSize) {
        var pageable = PageRequest.of(pageNumber - 1, pageMaxSize);
        return ResponseEntity.ok(houseService.getAll(pageable));
    }

    @GetMapping("/owner/{personuuid}/alltime")
    @Operation(summary = "Get Houses", description = "Get Houses For All Time By Person (Owner) UUID")
    public ResponseEntity<List<HouseResponse>> getOwnerHousesAllTime(@PathVariable("personuuid") UUID personUuid) {
        return ResponseEntity.ok(houseService.getOwnerHousesAllTime(personUuid));
    }

    @GetMapping("/tenant/{personuuid}/alltime")
    @Operation(summary = "Get Houses", description = "Get Houses For All Time By Person (Tenant) UUID")
    public ResponseEntity<List<HouseResponse>> getTenantHousesAllTime(@PathVariable("personuuid") UUID personUuid) {
        return ResponseEntity.ok(houseService.getTenantHousesAllTime(personUuid));
    }

    @GetMapping("/fulltextsearch/{text}")
    @Operation(summary = "Houses Full Text Search", description = "Full Text Search By Fields: area, country, city, street, number")
    public ResponseEntity<List<HouseResponse>> fullTextSearch(@PathVariable("text") String text) {
        return ResponseEntity.ok(houseService.fullTextSearch(text));
    }

    @PostMapping
    @Operation(summary = "Create House", description = "Create New House")
    public ResponseEntity<HouseResponse> create(@RequestBody @Valid HouseRequest houseRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(houseService.create(houseRequest));
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Update House", description = "Update Previous House")
    public ResponseEntity<HouseResponse> update(@PathVariable("uuid") UUID uuid,
                                                @RequestBody @Valid HouseRequest houseRequest) {
        return ResponseEntity.ok(houseService.update(uuid, houseRequest));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete House", description = "Delete House By UUID")
    public ResponseEntity<Void> deleteByUuid(@PathVariable("uuid") UUID uuid) {
        houseService.deleteByUuid(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
