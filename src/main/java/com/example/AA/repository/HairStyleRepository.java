package com.example.AA.repository;

import com.example.AA.entity.HairStyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HairStyleRepository extends JpaRepository<HairStyle, Long> {

    Optional<HairStyle> findHairStyleByHairStyleId(Long id);

    HairStyle findByHairName(String hairName);


}