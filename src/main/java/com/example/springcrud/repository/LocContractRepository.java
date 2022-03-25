package com.example.springcrud.repository;


import com.example.springcrud.entity.LocBondStatus;
import com.example.springcrud.entity.LocContract;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocContractRepository extends CrudRepository<LocContract,Long> {
        LocContract findByNumContract(String num);
        List<LocContract> findByDateBeginAfter(LocalDate date);
        @Query("SELECT l FROM LocContract l JOIN FETCH l.locBondStatuses WHERE l.id = (:id)")
        LocContract  findByIdAndFetchContractEagerly(@Param("id") Long id);
        @Query("SELECT DISTINCT locContract from LocContract locContract LEFT OUTER JOIN FETCH locContract.locBondStatuses locBonStatuses")
        Iterable<LocContract> retrieveAll();
}

