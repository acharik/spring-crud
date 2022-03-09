package com.example.springcrud.repository;


import com.example.springcrud.entity.LocContract;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocContractRepository extends CrudRepository<LocContract,Long> {
        LocContract findByNumContract(String num);
        List<LocContract> findByDateBeginAfter(LocalDate date);

}
