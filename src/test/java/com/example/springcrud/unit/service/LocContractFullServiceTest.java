package com.example.springcrud.unit.service;

import com.example.springcrud.entity.LocContract;
import com.example.springcrud.repository.LocContractRepository;
import com.example.springcrud.service.LocContractFullService;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LocContractFullServiceTest {
    @Mock
    private LocContractRepository locContractRepository;
    @InjectMocks
    private LocContractFullService locContractFullService;

    @Test
    public void whenSaveLocContract_shouldReturnLocContract() {
        LocContract locContract = new LocContract();
        locContract.setDateEnd(LocalDate.of(2022, 1, 1));
        locContract.setDateBegin(LocalDate.of(2023, 2, 2));
        locContract.setNumContract("1234");
        locContract.setComment("test");
        locContract.setSum(new BigDecimal(233));
        when(locContractRepository.save(ArgumentMatchers.any(LocContract.class))).thenReturn(locContract);
        LocContract created = locContractFullService.saveLocContract(locContract);
        assertThat(created.getNumContract()).isSameAs(locContract.getNumContract());
        assertThat(created.getDateBegin()).isSameAs(locContract.getDateBegin());
        assertThat(created.getDateEnd()).isSameAs(locContract.getDateEnd());
        assertThat(created.getSum()).isSameAs(locContract.getSum());
        verify(locContractRepository).save(locContract);
    }

    @Test
    public void shouldReturnAllLocContracts() {
        List<LocContract> locContractList = new ArrayList();
        locContractList.add(new LocContract());

        given(locContractRepository.retrieveAll()).willReturn(locContractList);

        List<LocContract> expected = locContractFullService.getContractFull(null, null);

        assertEquals(expected, locContractList);
        verify(locContractRepository, times(2)).retrieveAll();
    }

    @Test
    public void whenGivenId_shouldDeleteLocContract_ifFound() {
        LocContract locContract = new LocContract();
        locContract.setNumContract("224");
        locContract.setId(1L);

        when(locContractRepository.findByIdAndFetchContractEagerly(
                locContract.getId())).thenReturn(locContract);

        locContractFullService.deleteById(locContract.getId());
        verify(locContractRepository).deleteById(locContract.getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void should_throw_exception_when_DeleteLocContract_doesnt_exist() {
        LocContract locContract = new LocContract();
        locContract.setNumContract("2333");
        locContract.setId(234L);

        given(locContractRepository.findByIdAndFetchContractEagerly(anyLong())).willReturn(null);
        locContractFullService.deleteById(locContract.getId());

    }

    @Test
    public void whenGivenId_shouldUpdateLocContract_ifFound() {
        LocContract locContract = new LocContract();
        locContract.setId(1L);
        locContract.setDateEnd(LocalDate.of(2022, 1, 1));
        locContract.setDateBegin(LocalDate.of(2023, 2, 2));
        locContract.setNumContract("Test num");

        LocContract newLocContract = new LocContract();
        newLocContract.setDateEnd(LocalDate.of(2022, 1, 1));
        newLocContract.setDateBegin(LocalDate.of(2023, 2, 2));
        newLocContract.setId(1L);
        newLocContract.setNumContract("Test num new");

        given(locContractRepository.findByIdAndFetchContractEagerly(locContract.getId())).willReturn(locContract);
        locContractFullService.saveLocContract(newLocContract, locContract.getId());

        verify(locContractRepository).save(newLocContract);
        verify(locContractRepository).findByIdAndFetchContractEagerly(locContract.getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void should_throw_exception_when_UpdateLocContract_doesnt_exist() {
        LocContract locContract = new LocContract();
        locContract.setId(1L);
        locContract.setDateEnd(LocalDate.of(2022, 1, 1));
        locContract.setDateBegin(LocalDate.of(2023, 2, 2));
        locContract.setNumContract("Test num");

        LocContract newLocContract = new LocContract();
        newLocContract.setDateEnd(LocalDate.of(2022, 1, 1));
        newLocContract.setDateBegin(LocalDate.of(2023, 2, 2));
        newLocContract.setId(1L);
        newLocContract.setNumContract("Test num new");

        given(locContractRepository.findByIdAndFetchContractEagerly(anyLong())).willReturn(null);
        locContractFullService.saveLocContract(newLocContract, locContract.getId());
    }

    @Test
    public void whenGivenId_shouldReturnLocContract_ifFound() {
        LocContract locContract = new LocContract();
        locContract.setId(89L);

        when(locContractRepository.findByIdAndFetchContractEagerly(locContract.getId())).thenReturn(locContract);

        Optional<LocContract> expected = locContractFullService.getById(locContract.getId());

        assertThat(expected.orElse(new LocContract())).isSameAs(locContract);
        verify(locContractRepository, times(2)).findByIdAndFetchContractEagerly(locContract.getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void should_throw_exception_when_locContract_doesnt_exist() {
        LocContract locContract = new LocContract();

        locContract.setId(234L);

        given(locContractRepository.findByIdAndFetchContractEagerly(anyLong())).willReturn(null);
        locContractFullService.getById(locContract.getId());
    }
}
