package com.example.springcrud.service;

import com.example.springcrud.entity.LocContract;
import com.example.springcrud.repository.LocContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocContractFullService {
    private final LocContractRepository locContractRepository;
    public List<LocContract> getContractFull(LocalDate date, Long count){
        List<LocContract> locContractList = (List<LocContract>) locContractRepository.retrieveAll();
        if(locContractList.isEmpty()){
            throw new NoSuchElementException("Запись не найдена");
        }
        if(date == null && count == null){
            return (List<LocContract>) locContractRepository.retrieveAll();
        }
        if(date == null){
            return locContractList.stream().limit(count).collect(Collectors.toList());
        }
        if(count == null){
            return locContractRepository.findByDateBeginAfter(date);
        }
        return locContractRepository.findByDateBeginAfter(date).stream().limit(count).collect(Collectors.toList());

    }
    public Optional<LocContract> getById(Long id){
        Optional<LocContract> locContract = locContractRepository.findByIdAndFetchContractEagerly(id);
        if(locContract.isPresent()){

            return locContractRepository.findByIdAndFetchContractEagerly(id);

        }
        throw new NoSuchElementException("Запись не найдена");
    }
    public LocContract saveLocContract(LocContract locContract)  {
        if(dateCheck(locContract)){return locContractRepository.save(locContract);}
        else throw new DateTimeException("Дата не входит в диапазон значений");
    }
    public LocContract saveLocContract(LocContract locContract, Long id)  {
        Optional<LocContract> locContract2 = locContractRepository.findByIdAndFetchContractEagerly(id);
        if(locContract2.isEmpty()){
            throw new NoSuchElementException("Запись не найдена");
        }
        locContract.setId(id);
        if(dateCheck(locContract)){return locContractRepository.save(locContract);}
        else throw new DateTimeException("Дата не входит в диапазон значений");
    }
    private boolean dateCheck(LocContract locContract) {
        LocalDate dateBegin = locContract.getDateBegin();
        LocalDate dateEnd = locContract.getDateEnd();
        LocalDate dateCheckMax = LocalDate.of(2100, 12, 31);
        LocalDate dateCheckMin = LocalDate.of(1950, 1, 1);
        return dateBegin.isBefore(dateCheckMax) && dateBegin.isAfter(dateCheckMin) &&
                dateEnd.isBefore(dateCheckMax) && dateEnd.isAfter(dateCheckMin);
    }
    public void deleteById(Long id){

        Optional<LocContract> locContract = locContractRepository.findByIdAndFetchContractEagerly(id);
        if(locContract.isEmpty()){
            throw new NoSuchElementException("Запись не найдена");
        }
        locContractRepository.deleteById(id);
    }

}
