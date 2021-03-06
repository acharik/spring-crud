package com.example.springcrud.repository;


import com.example.springcrud.entity.LocContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocContractRepository extends JpaRepository<LocContract,Long> {
        LocContract findByNumContract(String num);
        List<LocContract> findByDateBeginAfter(LocalDate date);
        @Query("SELECT l FROM LocContract l    LEFT OUTER JOIN FETCH l.locBondStatuses WHERE l.id = (:id)")
        Optional<LocContract> findByIdAndFetchContractEagerly(@Param("id") Long id);
        @Query("SELECT DISTINCT locContract from LocContract locContract LEFT OUTER JOIN FETCH locContract.locBondStatuses locBonStatuses")
        Iterable<LocContract> retrieveAll();


        @Query("SELECT new LocContract (l.id, l.dateBegin, l.dateEnd, l.numContract, l.sum, l.comment) from LocContract l where l.id = :longs")
        Optional<LocContract> findById(Long longs);

        @Override
        @Query("SELECT new LocContract (l.id, l.dateBegin, l.dateEnd, l.numContract, l.sum, l.comment) from LocContract l ")
        List<LocContract> findAll();
}

