package com.example.AA.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long portfolioId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user; // 01

    @Column
    private String name;

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

    @Column(name = "image_url_1", nullable = true)
    private String imageUrl1;

    @Column(name = "image_url_2", nullable = true)
    private String imageUrl2;

    @Column(name = "image_url_3", nullable = true)
    private String imageUrl3;

    @Column(name = "image_url_4", nullable = true)
    private String imageUrl4;

    @OneToMany(mappedBy = "portfolio", fetch = FetchType.LAZY)
    private List<PortfolioHairStyle> portfolioHairStyles;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_advertise", nullable = true)
    private Integer isAdvertise;

    @Column
    private String styling1;

    @Column
    private String cost1;

    @Column
    private String styling2;

    @Column
    private String cost2;

    @Column
    private String styling3;

    @Column
    private String cost3;

    @Column
    private String styling4;

    @Column
    private String cost4;

    @Builder
    public Portfolio(User user, String name, String phoneNumber, String workplace,
                     String snsAddress, String introduction, int likesCount,
                     String styling1, String cost1, String styling2,
                     String cost2, String styling3, String cost3, String styling4, String cost4,
                     LocalDateTime createdAt, Integer isAdvertise) {
        this.user = user;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.workplace = workplace;
        this.snsAddress = snsAddress;
        this.introduction = introduction;
        this.likesCount = likesCount;
        this.styling1 = styling1;
        this.cost1 = cost1;
        this.styling2 = styling2;
        this.cost2 = cost2;
        this.styling3 = styling3;
        this.cost3 = cost3;
        this.styling4 = styling4;
        this.cost4 = cost4;
        this.createdAt = LocalDateTime.now();
        this.isAdvertise = isAdvertise;
    }
    public void uploadImageUrls(String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4) {
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.imageUrl4 = imageUrl4;
    }

    public void uploadProfileUrl(String profileURL) {
        this.profileURL = profileURL;
    }
}

