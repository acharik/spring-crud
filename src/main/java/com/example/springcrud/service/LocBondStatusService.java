package com.example.springcrud.service;

import com.example.springcrud.entity.LocBondStatus;
import com.example.springcrud.entity.LocContract;
import com.example.springcrud.repository.LocBondStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocBondStatusService {
    private final LocBondStatusRepository locBondStatusRepository;
    public Iterable<LocBondStatus> getAll(){
        return  locBondStatusRepository.findAll();
    }
    public LocBondStatus saveLocBondStatus(LocBondStatus locBondStatus){
        List<LocBondStatus>locBondStatuses = locBondStatusRepository.findByLocContractId(locBondStatus.getLocContractId());
        if(locBondStatuses.isEmpty()){
            return locBondStatusRepository.save(locBondStatus);
        }
        LocalDate date = locBondStatuses.stream().map(LocBondStatus::getDateStatus).max(LocalDate::compareTo).get();
        if(date.plusMonths(1).equals(locBondStatus.getDateStatus())){
        return locBondStatusRepository.save(locBondStatus);
        }else throw new DateTimeException("для договора номер "+locBondStatuses.get(0).getLocContractId().getNumContract()
                + " нет информации за "+date.plusMonths(1));
    }
    public LocBondStatus saveLocBondStatus(LocBondStatus locBondStatus,Long id){
        locBondStatus.setId(id);
        return locBondStatusRepository.save(locBondStatus);
    }
    public void deleteById(Long id){
        locBondStatusRepository.deleteById(id);
    }

}
