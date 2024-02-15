package com.example.AA.entity.enumtype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Getter
@Slf4j
@RequiredArgsConstructor
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
                log.info("같은 단어 존재");
                return true;
            }
        }
        
        return false;
    }
   
}
