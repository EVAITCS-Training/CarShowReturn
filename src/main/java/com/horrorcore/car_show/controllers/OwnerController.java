package com.horrorcore.car_show.controllers;

import com.horrorcore.car_show.dtos.OwnerRequest;
import com.horrorcore.car_show.dtos.OwnerResponse;
import com.horrorcore.car_show.services.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<OwnerResponse>> ownerIndex(){
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    @PostMapping(value = {"/create", "/create/"})
    public ResponseEntity<OwnerResponse> createOwner(@Valid @RequestBody OwnerRequest ownerRequest){
        return ResponseEntity.created(null).body(ownerService.createOwner(ownerRequest));
    }

    @PutMapping(value = {"/{id}/update"})
    public ResponseEntity<OwnerResponse> updateOwnerRequest(@Valid @RequestBody OwnerRequest ownerRequest) {
        return ResponseEntity.created(null).body(ownerService.createOwner(ownerRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable long id){
        ownerService.deleteOwnerById(id);
        return ResponseEntity.ok().build();
    }

}