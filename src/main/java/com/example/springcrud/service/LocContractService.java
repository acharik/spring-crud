package com.example.springcrud.service;

import com.example.springcrud.entity.LocContract;
import com.example.springcrud.repository.LocContractRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class LocContractService {
    private final LocContractRepository locContractRepository;
        public LocContract saveLocContract(LocContract locContract)  {
            LocalDate dateBegin = locContract.getDateBegin();
            LocalDate dateEnd = locContract.getDateEnd();
            LocalDate dateCheckMax = LocalDate.of(2100,12,31);
            LocalDate dateCheckMin = LocalDate.of(1950,1,1);
            if(dateBegin.isBefore(dateCheckMax)&& dateBegin.isAfter(dateCheckMin)&&
                    dateEnd.isBefore(dateCheckMax)&&dateEnd.isAfter(dateCheckMin)){
            return locContractRepository.save(locContract);}
            else throw new DateTimeException("Дата не входит в диапазон значений");
        }
    public Optional<LocContract> getById(Long id){
            Optional<LocContract> locContract = locContractRepository.findById(id);
            if(locContract.isEmpty()){
                throw new NoSuchElementException("Запись не найдена");
            }
            return locContractRepository.findById(id);
    }
    public void deleteById(Long id){
        locContractRepository.deleteById(id);
    }
    public LocContract getContractByNum(String num){return locContractRepository.findByNumContract(num);}
    public List<LocContract> getContract(LocalDate date, Long count){

            List<LocContract> locContractList = (List<LocContract>) locContractRepository.findAll();
            if(locContractList.isEmpty()){
                throw new NoSuchElementException("Запись не найдена");
            }
        if(date == null && count == null){
            return (List<LocContract>) locContractRepository.findAll();
        }
        if(date == null){
            return locContractList.stream().limit(count).collect(Collectors.toList());
        }
        if(count == null){
            return locContractRepository.findByDateBeginAfter(date);
        }
         return locContractRepository.findByDateBeginAfter(date).stream().limit(count).collect(Collectors.toList());
    }
}

