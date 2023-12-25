package com.example.AA.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUSET("ROLE_GUEST", "손님"),
    USER("ROLE_DESIGNER", "디자이너");

    private final String key;
    private final String title;
}