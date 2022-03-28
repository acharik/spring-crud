package com.example.springcrud.repository;

import com.example.springcrud.entity.LocBondStatus;
import com.example.springcrud.entity.LocContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocBondStatusRepository extends JpaRepository<LocBondStatus,Long> {
        List<LocBondStatus> findByLocContractId(LocContract locContract);

}
