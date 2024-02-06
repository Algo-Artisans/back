package com.example.AA.repository;

import com.example.AA.entity.Portfolio;
import com.example.AA.entity.User;
import com.example.AA.entity.enumtype.HairName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Portfolio findPortfolioByUser(User user);

    Portfolio findPortfolioByPortfolioId(Long portfolioId);
}
