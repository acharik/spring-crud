package com.example.springcrud.unit.controller;

import com.example.springcrud.JsonUtil;
import com.example.springcrud.controller.LocBondStatusController;
import com.example.springcrud.controller.MainController;
import com.example.springcrud.entity.LocBondStatus;
import com.example.springcrud.entity.LocContract;
import com.example.springcrud.service.LocBondStatusService;
import com.example.springcrud.service.LocContractService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LocBondStatusController.class)
public class LocBondStatusControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocBondStatusService service;
    @MockBean
    private LocContractService service1;
    private LocContract locContract = new LocContract(1L, LocalDate.of(2021, 2, 2),
            LocalDate.of(2022, 1, 1), "1234", new BigDecimal(233), "test",null);

    private LocBondStatus locBondStatus = new LocBondStatus(1L, LocalDate.of(2020,1,1),
            new BigDecimal(20),new BigDecimal(200),locContract);
    @Test
    public void createLocBondStatus_whenPostMethod() throws Exception {


        given(service.saveLocBondStatus(locBondStatus)).willReturn(locBondStatus);

        mockMvc.perform(post("/spring-crud/v1/loc_bond_status")
                        .contentType(APPLICATION_JSON)
                        .content(JsonUtil.toJson(locBondStatus)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateStatus").value(locBondStatus.getDateStatus()));
    }
  @Test
  public void listAllLocBondStatus_whenGetMethod()
          throws Exception {


      List<LocBondStatus> allEmployees = Arrays.asList(locBondStatus);

      given(service.getAll()).willReturn(allEmployees);

      mockMvc.perform(get("/spring-crud/v1/loc_bond_status")
                      .contentType(APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
              .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateStatus", Matchers.is(locBondStatus.getDateStatus().toString()   )));
  }
    @Test
    public void removeLocBondStatusById_whenDeleteMethod() throws Exception {


        doNothing().when(service).deleteById(locBondStatus.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/spring-crud/v1/loc_bond_status/" + locBondStatus.getId().toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_throw_exception_when_locBondStatus_doesnt_exist() throws Exception {


        Mockito.doThrow(new NoSuchElementException(String.valueOf(locBondStatus.getId()))).when(service).deleteById(locBondStatus.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/spring-crud/v1/loc_bond_status/" + locBondStatus.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void updateLocBondStatus_whenPut() throws Exception {


        given(service.saveLocBondStatus(locBondStatus, locBondStatus.getId())).willReturn(locBondStatus);

        mockMvc.perform(put("/spring-crud/v1/loc_bond_status/" + locBondStatus.getId().toString())
                        .content(JsonUtil.toJson(locBondStatus))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("dateStatus", Matchers.is(locBondStatus.getDateStatus())));
    }
}
