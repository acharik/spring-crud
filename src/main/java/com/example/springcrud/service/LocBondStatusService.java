package com.example.springcrud.service;

import com.example.springcrud.entity.LocBondStatus;
import com.example.springcrud.entity.LocContract;
import com.example.springcrud.repository.LocBondStatusRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class LocBondStatusService {
    private final LocBondStatusRepository locBondStatusRepository;
    private final LocContractFullService locContractService;

    public Iterable<LocBondStatus> getAll() {
        return locBondStatusRepository.findAll();
    }

    public LocBondStatus saveLocBondStatus(LocBondStatus locBondStatus) {
        Optional<LocContract> locContract = locContractService.getById(locBondStatus.getLocContractId().getId());
        if (locContract.isEmpty()) {
            throw new NoSuchElementException("Контракта не существует");
        }
        List<LocBondStatus> locBondStatuses = locBondStatusRepository.findByLocContractId(locBondStatus.getLocContractId());
        if (locBondStatuses.isEmpty()) {
            return locBondStatusRepository.save(locBondStatus);
        }
        if (locBondStatus.getDateStatus().getDayOfMonth() != 1) {
            throw new DateTimeException("Должно быть введено первое число месяца");
        }
        LocalDate date = locBondStatuses.stream().map(LocBondStatus::getDateStatus).max(LocalDate::compareTo).get();
        if (date.equals(locBondStatus.getDateStatus())) {
            throw new DateTimeException("за дату " + date + " для договора номер "
                    + locBondStatuses.get(0).getLocContractId().getNumContract() + " уже есть запись состояния");
        }

        if (date.plusMonths(1).equals(locBondStatus.getDateStatus())) {
            return locBondStatusRepository.save(locBondStatus);
        } else
            throw new DateTimeException("для договора номер " + locBondStatuses.get(0).getLocContractId().getNumContract()
                    + " нет информации за " + date.plusMonths(1));
    }

    public LocBondStatus saveLocBondStatus(LocBondStatus locBondStatus, Long id) {
        Optional<LocBondStatus> locBondStatus1 = locBondStatusRepository.findById(id);
        if (locBondStatus1.isEmpty()) {
            throw new NoSuchElementException("Запись не найдена");
        }
        locBondStatus.setId(id);
        return locBondStatusRepository.save(locBondStatus);
    }

    public void deleteById(Long id) {
        Optional<LocBondStatus> locBondStatus1 = locBondStatusRepository.findById(id);
        if (locBondStatus1.isEmpty()) {
            throw new NoSuchElementException("Запись не найдена");
        }
        locBondStatusRepository.deleteById(id);
    }

}
