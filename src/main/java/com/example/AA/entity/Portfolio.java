package com.example.AA.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long portfolioId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column
    private String gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private String workplace;

    @Column(name = "sns_address")
    private String snsAddress;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "likes_count")
    private int likesCount;

    @Column(name = "profile_url")
    private String profileURL;

    @Builder
    public Portfolio(User user, String gender, String phoneNumber, String workplace,
                     String snsAddress, String introduction, int likesCount,
                     String profileURL) {
        this.user = user;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.workplace = workplace;
        this.snsAddress = snsAddress;
        this.introduction = introduction;
        this.likesCount = likesCount;
        this.profileURL = profileURL;
    }
}

