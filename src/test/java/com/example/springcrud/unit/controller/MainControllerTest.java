package com.example.springcrud.unit.controller;

import com.example.springcrud.JsonUtil;
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
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocContractService service;

    @MockBean
    private LocBondStatusService locBondStatusService;
    private LocContract locContract = new LocContract(1L, LocalDate.of(2021, 2, 2),
            LocalDate.of(2022, 1, 1), "1234", new BigDecimal(233), "test",null);

    @Test
    public void createLocContract_whenPostMethod() throws Exception {


        given(service.saveLocContract(locContract)).willReturn(locContract);

        mockMvc.perform(MockMvcRequestBuilders.post("/spring-crud/v1/loc_contract")
                        .contentType(APPLICATION_JSON)
                        .content(JsonUtil.toJson(locContract)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numContract").value(locContract.getNumContract()));
    }

    @Test
    public void listAllLocContract_whenGetMethod()
            throws Exception {


        List<LocContract> allEmployees = Arrays.asList(locContract);

        given(service.getContract(null, null)).willReturn(allEmployees);

        mockMvc.perform(get("/spring-crud/v1/loc_contract")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].numContract", Matchers.is(locContract.getNumContract())));
    }

    @Test
    public void removeLocContractById_whenDeleteMethod() throws Exception {
        LocContract locContract = new LocContract();
        locContract.setDateEnd(LocalDate.of(2022, 1, 1));
        locContract.setDateBegin(LocalDate.of(2021, 2, 2));
        locContract.setNumContract("1234");
        locContract.setComment("test");
        locContract.setSum(new BigDecimal(233));
        locContract.setId(1L);

        doNothing().when(service).deleteById(locContract.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/spring-crud/v1/loc_contract/" + locContract.getId().toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_throw_exception_when_locContract_doesnt_exist() throws Exception {


        Mockito.doThrow(new NoSuchElementException(String.valueOf(locContract.getId()))).when(service).deleteById(locContract.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/spring-crud/v1/loc_contract/" + locContract.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void locContractById_whenGetMethod() throws Exception {


        given(service.getById(locContract.getId())).willReturn(Optional.ofNullable(locContract));

        mockMvc.perform(get("/spring-crud/v1/loc_contract/" + locContract.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("numContract", Matchers.is(locContract.getNumContract())));
    }

    @Test
    public void updateLocContract_whenPut() throws Exception {


        given(service.saveLocContract(locContract, locContract.getId())).willReturn(locContract);

        mockMvc.perform(put("/spring-crud/v1/loc_contract/" + locContract.getId().toString())
                        .content(JsonUtil.toJson(locContract))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("numContract", Matchers.is(locContract.getNumContract())));
    }
}


