package com.example.AA.repository;

import com.example.AA.entity.Hello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelloRepository extends JpaRepository<Hello , Long>{

    @Override
    List<Hello> findAll();

}
