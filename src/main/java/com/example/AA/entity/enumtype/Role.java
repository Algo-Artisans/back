package com.example.AA.entity.enumtype;

        import lombok.Getter;
        import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("ROLE_USER", "유저"),
    GUEST("ROLE_GUEST", "손님"),
    DESIGNER("ROLE_DESIGNER", "디자이너"),
    ADMINISTRATOR("ROLE_ADMINISTRATOR","관리자");

    private final String key;
    private final String title;
}