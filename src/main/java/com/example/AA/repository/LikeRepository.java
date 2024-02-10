package com.example.AA.repository;

import com.example.AA.entity.Like;
import com.example.AA.entity.Portfolio;
import com.example.AA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findLikesByUser(User user);

    List<Like> findLikesByPortfolio(Portfolio portfolio);
}
