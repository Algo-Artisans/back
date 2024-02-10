package com.example.AA.entity.enumtype;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FaceShape {
    Heart("FACESHAPE_HEART","하트형"),
    Oblong("FACESHAPE_OBLONG","긴형"),
    Round("FACESHAPE_ROUND", "둥근형"),
    Square("FACESHAPE_SQUARE", "사각형"),
    Oval("FACESHAPE_OVAL", "계란형");

    private final String value;
    private final String title;
}

