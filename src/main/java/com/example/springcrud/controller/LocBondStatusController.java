package com.example.springcrud.controller;

import com.example.springcrud.entity.LocBondStatus;
import com.example.springcrud.entity.LocContract;
import com.example.springcrud.service.LocBondStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.zip.DataFormatException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("spring-crud/v1/loc_bond_status")
public class LocBondStatusController {
    private final LocBondStatusService locBondStatusService;
    @GetMapping
    public Iterable<LocBondStatus> getAll(){
        return locBondStatusService.getAll();
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LocBondStatus add (@Validated @RequestBody LocBondStatus locBondStatus){

        return locBondStatusService.saveLocBondStatus(locBondStatus);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        locBondStatusService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    LocBondStatus updateLocContract( @Validated @RequestBody LocBondStatus locBondStatus, @PathVariable("id") Long id){
        return locBondStatusService.saveLocBondStatus(locBondStatus,id);
    }

}

