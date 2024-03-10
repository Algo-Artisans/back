package com.example.AA.repository;

import com.example.AA.entity.Portfolio;
import com.example.AA.entity.PortfolioHairStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PortfolioHairStyleRepository extends JpaRepository<PortfolioHairStyle, Long> {
    List<PortfolioHairStyle> findPortfolioHairStyleByPortfolio(Portfolio portfolio);


    List<PortfolioHairStyle> findByPortfolio(Portfolio portfolio);
}
