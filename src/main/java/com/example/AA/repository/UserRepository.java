package com.example.AA.repository;

import com.example.AA.entity.Portfolio;
import com.example.AA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByKakaoId(Long kakaoId);

}
