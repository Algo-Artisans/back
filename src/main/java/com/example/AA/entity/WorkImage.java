package com.example.AA.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "work_image")
public class WorkImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_image_id")
    private Long workImageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio; //jiwon 1

    @Column(name = "image_url_1", nullable = true) //s3.url
    private String imageUrl1;

    @Column(name = "image_url_2", nullable = true)
    private String imageUrl2;

    @Column(name = "image_url_3", nullable = true)
    private String imageUrl3;

    @Column(name = "image_url_4", nullable = true)
    private String imageUrl4;

    @Builder
    public WorkImage(Portfolio portfolio, String imageUrl1,String imageUrl2,String imageUrl3,String imageUrl4) {
        this.portfolio = portfolio;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.imageUrl4 = imageUrl4;
    }
}
