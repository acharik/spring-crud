package com.example.springcrud.unit.service;

import com.example.springcrud.entity.LocBondStatus;
import com.example.springcrud.entity.LocContract;
import com.example.springcrud.repository.LocBondStatusRepository;
import com.example.springcrud.repository.LocContractRepository;
import com.example.springcrud.service.LocBondStatusService;
import com.example.springcrud.service.LocContractService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocBondStatusServiceTest {
    @Mock
    private LocBondStatusRepository locBondStatusRepository;
    @InjectMocks
    private LocBondStatusService locBondStatusService;
    @Test
    public void whenSaveLocBondStatus_shouldReturnLocBondStatus(){
        LocBondStatus   locBondStatus = new LocBondStatus();
        locBondStatus.setDateStatus(LocalDate.of(2020,1,1));
        locBondStatus.setCancelMain(new BigDecimal(20));
        locBondStatus.setTotalMain(new BigDecimal(200));
        locBondStatus.setLocContractId(new LocContract());
        when(locBondStatusRepository.save(ArgumentMatchers.any(LocBondStatus.class))).thenReturn(locBondStatus);
        LocBondStatus created = locBondStatusService.saveLocBondStatus(locBondStatus);
        assertThat(created.getDateStatus()).isSameAs(locBondStatus.getDateStatus());

        verify(locBondStatusRepository).save(locBondStatus);
    }
    @Test
    public void shouldReturnAllLocBondStatus() {
        List<LocBondStatus> locBondStatusList = new ArrayList<>();

        locBondStatusList.add(new LocBondStatus());

        given(locBondStatusRepository.findAll()).willReturn(locBondStatusList);

        List<LocBondStatus> expected = (List<LocBondStatus>) locBondStatusService.getAll();

        assertEquals(expected, locBondStatusList);
        verify(locBondStatusRepository).findAll();
    }
    @Test
    public void whenGivenId_shouldDeleteLocBondStatus_ifFound(){
        LocBondStatus locBondStatus = new LocBondStatus();
        locBondStatus.setId(1L);
        locBondStatus.setTotalMain(new BigDecimal(20));

        when(locBondStatusRepository.findById(locBondStatus.getId())).thenReturn(Optional.of(locBondStatus));

        locBondStatusService.deleteById(locBondStatus.getId());
        verify(locBondStatusRepository).deleteById(locBondStatus.getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void should_throw_exception_when_DeleteLocBondStatus_doesnt_exist() {
        LocBondStatus locBondStatus = new LocBondStatus();
        locBondStatus.setId(1L);
        locBondStatus.setTotalMain(new BigDecimal(20));

        given(locBondStatusRepository.findById(anyLong())).willReturn(Optional.empty());
        locBondStatusService.deleteById(locBondStatus.getId());

    }
    @Test
    public void whenGivenId_shouldUpdateLocBondStatus_ifFound() {
        LocBondStatus   locBondStatus = new LocBondStatus();
        locBondStatus.setDateStatus(LocalDate.of(2020,1,1));
        locBondStatus.setCancelMain(new BigDecimal(20));
        locBondStatus.setTotalMain(new BigDecimal(200));

        LocBondStatus   newLocBondStatus = new LocBondStatus();
        locBondStatus.setDateStatus(LocalDate.of(2020,1,1));
        locBondStatus.setCancelMain(new BigDecimal(20));
        locBondStatus.setTotalMain(new BigDecimal(2000));

        given(locBondStatusRepository.findById(locBondStatus.getId())).willReturn(Optional.of(locBondStatus));
        locBondStatusService.saveLocBondStatus(newLocBondStatus,locBondStatus.getId());

        verify(locBondStatusRepository).save(newLocBondStatus);
        verify(locBondStatusRepository).findById(locBondStatus.getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void should_throw_exception_when_UpdateLocBondStatus_doesnt_exist() {
        LocBondStatus   locBondStatus = new LocBondStatus();
        locBondStatus.setDateStatus(LocalDate.of(2020,1,1));
        locBondStatus.setCancelMain(new BigDecimal(20));
        locBondStatus.setTotalMain(new BigDecimal(200));
        locBondStatus.setId(1L);
        LocBondStatus   newLocBondStatus = new LocBondStatus();
        locBondStatus.setDateStatus(LocalDate.of(2020,1,1));
        locBondStatus.setCancelMain(new BigDecimal(20));
        locBondStatus.setTotalMain(new BigDecimal(2000));
        locBondStatus.setId(1L);

        given(locBondStatusRepository.findById(anyLong())).willReturn(Optional.empty());
        locBondStatusService.saveLocBondStatus(newLocBondStatus, locBondStatus.getId());
    }

}
