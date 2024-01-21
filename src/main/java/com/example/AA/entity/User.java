package com.example.AA.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;


import javax.persistence.*;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Where(clause = "delete_flag=0")
@Entity
@Table(name = "user")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name="kakao_id")
    @NotNull
    private Long kakaoId;

    @Column
    @NotNull
    private String nickname;

    @Column
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "face_shape_best")
    private FaceShape faceShapeBest;

    @Enumerated(EnumType.STRING)
    @Column(name = "face_shape_worst")
    private FaceShape faceShapeWorst;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name="delete_flag")
    @NotNull
    private Boolean deleteFlag;

    @Builder
    public User(Long kakaoId, String nickname, String image) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.image = image;
        this.faceShapeBest = null;
        this.faceShapeWorst = null;
        this.deleteFlag = false;
    }


    public void updateProfile(String nickname) {
        this.nickname = nickname;
    }

    public void updateImage(String fileurl) {
        this.image = fileurl;
    }

    public void updateFaceShapeBest(FaceShape faceShapeBest) {
        this.faceShapeBest = faceShapeBest;
    }

    public void updateFaceShapeWorst(FaceShape faceShapeWorst) {
        this.faceShapeWorst = faceShapeWorst;
    }

    public void setRole(String role) {
        this.role = Role.valueOf(role);
    }

    public boolean isDesigner() {
        return this.getRole() == Role.DESIGNER;
    }

    public void updateDeleteFlag() {
        this.deleteFlag = true;
    }
}



