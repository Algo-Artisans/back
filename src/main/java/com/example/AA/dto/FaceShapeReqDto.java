package com.example.AA.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@Data
public class FaceShapeReqDto {
    private String faceShapeBest;
    private String faceShapeWorst;

    @Builder
    public FaceShapeReqDto(String faceShapeBest,String faceShapeWorst) {
        this.faceShapeBest= faceShapeBest;
        this.faceShapeWorst= faceShapeWorst;
    }
}
