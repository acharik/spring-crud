package com.example.springcrud.controller;


import com.example.springcrud.entity.LocBondStatus;
import com.example.springcrud.entity.LocContract;
import com.example.springcrud.service.LocBondStatusService;
import com.example.springcrud.service.LocContractService;
import com.example.springcrud.view.ExcelView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("spring-crud/v1/loc_contract")
public class MainController {
    private final LocBondStatusService locBondStatusService;
    private final LocContractService locContractService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LocContract add (@Validated @RequestBody LocContract locContract)  {

            return locContractService.saveLocContract(locContract);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Optional<LocContract> getById(@PathVariable("id") Long id)  {

        return locContractService.getById(id);}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        locContractService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    LocContract updateLocContract( @Validated @RequestBody LocContract locContract, @PathVariable("id") Long id){
        return locContractService.saveLocContract(locContract,id);
    }

    @GetMapping(params = "num")
    @ResponseStatus(HttpStatus.OK)
    public LocContract getContractByNumber(@RequestParam String num){
        return locContractService.getContractByNum(num);
    }
   @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<LocContract> getContract(@RequestParam(required = false)
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                            @RequestParam(required = false) Long count){

        return locContractService.getContract(date,count);
    }
    @GetMapping("/downloadExel")
    public ModelAndView downloadExel(){
        List<LocContract> list = locContractService.getContract(null,null);
        ExcelView excelView = new ExcelView();
        Map<String, Object> map = new HashMap<>();

        map.put("LocContractList", list);
        return new ModelAndView(excelView, map);
    }
    @GetMapping("/full")
    public List<LocContract> getAllFull(){
        return locContractService.getContractFull();
    }
}






