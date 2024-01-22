package com.example.AA.repository;

import com.example.AA.entity.HairStyle;
import com.example.AA.entity.Portfolio;
import com.example.AA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HairStyleRepository extends JpaRepository<HairStyle, Long> {

    //Optional<HairStyle> findHairStyleByPortfolio(Portfolio portfolio);

    Optional<HairStyle> findHairStyleByHairStyleId(Long id);
}