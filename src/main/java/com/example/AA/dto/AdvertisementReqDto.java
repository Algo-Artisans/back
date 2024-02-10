package com.example.AA.dto;

import com.example.AA.entity.Portfolio;
import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@Data
public class AdvertisementReqDto {
    private boolean applied;

    @Builder
    public AdvertisementReqDto(boolean applied) {
        this.applied = applied;
    }
}