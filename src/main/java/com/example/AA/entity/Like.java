package com.example.AA.entity;

import com.example.AA.entity.enumtype.FaceShape;
import com.example.AA.entity.enumtype.HairName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="portfolio_id")
    private Portfolio portfolio;

    @Column(name="created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Like(User user, Portfolio portfolio, LocalDateTime createdAt) {
        this.user = user;
        this.portfolio= portfolio;
        this.createdAt = createdAt;
    }

}
