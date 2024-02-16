package com.example.AA.entity.enumtype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@RequiredArgsConstructor
public enum HairName {
    // Heart
    SHORT_C_PERM("HAIRNAME_SHORT_C_PERM","단발 C컬펌"),
    BOB_CUT("HAIRNAME_BOB_CUT","보브컷"),
    IRON_PERM("HAIRNAME_IRON_PERM","아이롱펌"),
    // Oblong
    LAYERED_CUT("HAIRNAME_LAYERED_CUT","레이어드컷"),
    HIPPIE_PERM("HAIRNAME_HIPPIE_PERM","히피펌"),
    MIDDLE_LAYERED_PERM("HAIRNAME_MIDDLE_LAYERED_PERM","중단발 레이어드펌"),
    // Round
    STRAIGHT("HAIRNAME_STRAIGHT","스트레이트"),
    SHORT_CUT("HAIRNAME_SHORT_CUT","숏컷"),
    TASSEL_CUT("HAIRNAME_TASSEL_CUT","태슬컷"),
    // Square
    SHORT_LAYERED_PERM("HAIRNAME_SHORT_LAYERED_PERM","단발 레이어드펌"),
    SHAMING_CUT("HAIRNAME_SHAMING_CUT","샤밍컷"),
    HUSH_CUT("HAIRNAME_HUSH_CUT","허쉬컷"),
    // Oval
    SIDEBANG("HAIRNAME_SIDEBANG","사이드뱅"),
    LONG_C_PERM("HAIRNAME_LONG_C_PERM","장발 C컬펌"),
    BUILD_PERM("HAIRNAME_BUILD_PERM","빌드펌");

    private final String value;
    private final String title;


    public static HairName containsTitle(String title) {
        for (HairName hairName : HairName.values()) {
            if (hairName.title.equals(title)) {
                log.info("존재");
                return hairName;
            }
        }
        log.info("없음");
        return null;
    }

    public String getValue() {
        return value;
    }
}