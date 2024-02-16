package com.example.AA.repository;

import com.example.AA.entity.Portfolio;
import com.example.AA.entity.WorkImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkImageRepository extends JpaRepository<WorkImage, Long>{
    Optional<WorkImage> findWorkImageByPortfolio(Portfolio portfolio);

    List<WorkImage> findByPortfolio(Portfolio portfolio);
}
//