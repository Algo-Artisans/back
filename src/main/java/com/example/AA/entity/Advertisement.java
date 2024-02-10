package com.example.AA.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "advertisement")
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advertisement_id")
    private Long advertisementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Column(name = "applied")
    private boolean applied;

    @Builder
    public Advertisement(Portfolio portfolio, boolean applied) {
        this.portfolio = portfolio;
        this.applied = applied;
    }
}
