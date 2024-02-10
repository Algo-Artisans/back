package com.example.AA.dto;

import com.example.AA.entity.Portfolio;
import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@Data
public class AdvertisementResDto {
    private Long advertisementId;
    private Portfolio portfolio;
    private boolean applied;

    @Builder
    public AdvertisementResDto(Long advertisementId, Portfolio portfolio, boolean applied) {
        this.advertisementId = advertisementId;
        this.portfolio = portfolio;
        this.applied = applied;
    }
}
