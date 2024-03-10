package com.example.AA.entity.enumtype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum HairName {
    // Heart
    SHORT_C_PERM("단발 C컬펌"),
    BOB_CUT("보브컷"),
    IRON_PERM("아이롱펌"),
    // Oblong
    LAYERED_CUT("레이어드컷"),
    HIPPIE_PERM("히피펌"),
    MIDDLE_LAYERED_PERM("중단발 레이어드펌"),
    // Round
    STRAIGHT("스트레이트"),
    SHORT_CUT("숏컷"),
    TASSEL_CUT("태슬컷"),
    // Square
    SHORT_LAYERED_PERM("단발 레이어드펌"),
    SHAMING_CUT("샤밍컷"),
    HUSH_CUT("허쉬컷"),
    // Oval
    SIDEBANG("사이드뱅"),
    LONG_C_PERM("장발 C컬펌"),
    BUILD_PERM("빌드펌");

    private final String keyword;

    HairName(String keyword) {
        this.keyword = keyword;
    }

    public static boolean containsKeyword(String keyword) {
        for (HairName hairName : HairName.values()) {
            if (hairName.keyword.equals(keyword)) {
                return true;
            }
        }

        return false;
    }
}

/* INSERT INTO hair_style (face_shape, hair_name) VALUES
        ('FACESHAPE_HEART', "단발 C컬펌"),
        ('FACESHAPE_HEART', "보브컷"),
        ('FACESHAPE_HEART', "아이롱펌"),
        ('FACESHAPE_OBLONG', "레이어드컷"),
        ('FACESHAPE_OBLONG', "히피펌"),
        ('FACESHAPE_OBLONG', "중단발 레이어드펌"),
        ('FACESHAPE_ROUND', "스트레이트"),
        ('FACESHAPE_ROUND', "숏컷"),
        ('FACESHAPE_ROUND', "태슬컷"),
        ('FACESHAPE_SQUARE', "단발 레이어드펌"),
        ('FACESHAPE_SQUARE', "샤밍컷"),
        ('FACESHAPE_SQUARE', "허쉬컷"),
        ('FACESHAPE_OVAL', "사이드뱅"),
        ('FACESHAPE_OVAL', "장발 C컬펌"),
        ('FACESHAPE_OVAL', "빌드펌"); */