package com.example.AA.entity;

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
    private Long kakaoId;

    @Column
    @NotNull
    private String nickname;

    @Column
    private String image;


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
        this.deleteFlag = false;
    }

    public void setRole(String role) {
        this.role = Role.valueOf(role);
    }

    public void updateProfile(String nickname) {
        this.nickname = nickname;
    }

    public void updateImage(String fileurl) {
        this.image = fileurl;
    }


    public void updateDeleteFlag() {
        this.deleteFlag = true;
    }
}



