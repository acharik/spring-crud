package com.example.springcrud.controller;

import com.example.springcrud.entity.LocContract;
import com.example.springcrud.service.LocContractFullService;
import com.example.springcrud.service.LocContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("spring-crud/v1/loc_contract/full")
public class LocContractFullController {
    private final LocContractFullService locContractFullService;
    @GetMapping()
    public List<LocContract> getAllFull(@RequestParam(required = false)
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                        @RequestParam(required = false) Long count){
        return locContractFullService.getContractFull(date,count);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<LocContract> getById(@PathVariable("id") Long id)  {

        return locContractFullService.getById(id);}
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LocContract add (@Validated @RequestBody LocContract locContract)  {

        return locContractFullService.saveLocContract(locContract);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        locContractFullService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    LocContract updateLocContract( @Validated @RequestBody LocContract locContract, @PathVariable("id") Long id){
        return locContractFullService.saveLocContract(locContract,id);
    }
}
