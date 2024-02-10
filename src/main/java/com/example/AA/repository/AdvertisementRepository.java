package com.example.AA.repository;

import com.example.AA.entity.Advertisement;
import com.example.AA.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long>{
    Advertisement findByPortfolio(Portfolio portfolio);
}
