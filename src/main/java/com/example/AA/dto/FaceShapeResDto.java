package com.example.AA.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@Data
public class FaceShapeResDto {
    private String faceShapeBest;
    private String faceShapeWorst;

    @Builder
    public FaceShapeResDto(String faceShapeBest,String faceShapeWorst) {
        this.faceShapeBest= faceShapeBest;
        this.faceShapeWorst= faceShapeWorst;
    }
}
