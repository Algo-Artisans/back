package com.example.AA.entity;

import com.example.AA.entity.enumtype.Role;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;


import javax.persistence.*;

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

    @Column(name="kakao_nickname")
    @NotNull
    private String kakaoNickname;

    @Column
    private String image;

    @Column
    private String nickname;

    @Column
    private String gender;

    @Column(name = "face_shape_best")
    private String faceShapeBest;

    @Column(name = "face_shape_worst")
    private String faceShapeWorst;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name="delete_flag")
    @NotNull
    private Boolean deleteFlag;

    @Builder
    public User(Long kakaoId, String kakaoNickname, String image) {
        this.kakaoId = kakaoId;
        this.kakaoNickname = kakaoNickname;
        this.image = image;
        this.faceShapeBest = null;
        this.faceShapeWorst = null;
        this.deleteFlag = false;
    }



    public void updateFaceShapeBest(String faceShapeBest) {
        this.faceShapeBest = faceShapeBest;
    }

    public void updateFaceShapeWorst(String faceShapeWorst) {
        this.faceShapeWorst = faceShapeWorst;
    }

    public void updateGender(String gender){ this.gender = gender;}

    public  void updateNickname(String nickname){this.nickname = nickname;}

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



