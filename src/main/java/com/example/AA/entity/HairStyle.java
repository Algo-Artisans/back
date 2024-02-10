package com.example.AA.entity;

import com.example.AA.entity.enumtype.HairName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.AA.entity.enumtype.FaceShape;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "hair_style")
public class HairStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hair_style_id")
    private Long hairStyleId; // 1

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "face_shape_id",nullable = true)
    private FaceShape faceShape; //ㅣlong

    @Column(name = "hair_name",nullable = true)
    private HairName hairName; // 스트레이트

    @Builder
    public HairStyle(String hairName) {
        //this.faceShape = faceShape;
        this.hairName = HairName.valueOf(hairName);
    }
}

/*
INSERT INTO hair_style (face_shape, hair_name) VALUES
        ('FACESHAPE_HEART', 'HAIRNAME_SHORT_C_PERM'),
        ('FACESHAPE_HEART', 'HAIRNAME_BOB_CUT'),
        ('FACESHAPE_HEART', 'HAIRNAME_IRON_PERM'),
        ('FACESHAPE_OBLONG', 'HAIRNAME_LAYERED_CUT'),
        ('FACESHAPE_OBLONG', 'HAIRNAME_HIPPIE_PERM'),
        ('FACESHAPE_OBLONG', 'HAIRNAME_MIDDLE_LAYERED_PERM'),
        ('FACESHAPE_ROUND', 'HAIRNAME_STRAIGHT'),
        ('FACESHAPE_ROUND', 'HAIRNAME_SHORT_CUT'),
        ('FACESHAPE_ROUND', 'HAIRNAME_TASSEL_CUT'),
        ('FACESHAPE_SQUARE', 'HAIRNAME_SHORT_LAYERED_PERM'),
        ('FACESHAPE_SQUARE', 'HAIRNAME_SHAMING_CUT'),
        ('FACESHAPE_SQUARE', 'HAIRNAME_HUSH_CUT'),
        ('FACESHAPE_OVAL', 'HAIRNAME_SIDEBANG'),
        ('FACESHAPE_OVAL', 'HAIRNAME_LONG_C_PERM'),
        ('FACESHAPE_OVAL', 'HAIRNAME_BUILD_PERM');
*/