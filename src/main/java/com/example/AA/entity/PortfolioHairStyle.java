package com.example.AA.entity;


import java.util.Optional;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "portfolio_hair_style")
public class PortfolioHairStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_hair_style_id")
    private Long PortfolioHairStyleId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "portfolio_id",nullable = true)
    private Portfolio portfolio; // jiwon id1

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hair_style_id",nullable = true)
    private HairStyle hairStyle; // 스트레이트, c컬단발, 보브컷


    @Builder
    public PortfolioHairStyle(Portfolio portfolio, HairStyle hairStyle) {
        this.portfolio = portfolio;
        this.hairStyle = hairStyle;
    }
}

