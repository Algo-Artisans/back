package com.example.AA.repository;

import com.example.AA.entity.Portfolio;
import com.example.AA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Portfolio findPortfolioByUser(User user);

    Portfolio findPortfolioByPortfolioId(Long portfolioId);

    List<Portfolio> findByIsAdvertise(int i);
}
