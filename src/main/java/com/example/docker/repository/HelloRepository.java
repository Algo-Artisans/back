package com.example.docker.repository;

import com.example.docker.entity.Hello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelloRepository extends JpaRepository<Hello , Long>{

    @Override
    List<Hello> findAll();

}
