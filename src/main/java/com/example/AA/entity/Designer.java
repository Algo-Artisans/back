//package com.example.AA.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
//@Getter
//@Entity
//@Table(name = "designer")
//@PrimaryKeyJoinColumn(name = "user_id")
//public class Designer extends User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "designer_id")
//    private Long designerId;
//
//    @Column
//    private String gender;
//
//    @Column(name = "phone_number")
//    private String phoneNumber;
//
//    @Column
//    private String workplace;
//
//    @Column(name = "sns_address")
//    private String snsAddress;
//
//    @Column(name = "introduction")
//    private String introduction;
//
//    @Column(name = "likes_count")
//    private int likesCount;
//
//    @Column(name = "profile_url")
//    private String profileURL;
//
//    @Builder
//    public Designer(String gender, String phoneNumber, String workplace,
//                    String snsAddress, String introduction, int likesCount,
//                    String profileURL) {
//        //super(kakaoId, nickname, image);
//        this.gender = gender;
//        this.phoneNumber = phoneNumber;
//        this.workplace = workplace;
//        this.snsAddress = snsAddress;
//        this.introduction = introduction;
//        this.likesCount = likesCount;
//        this.profileURL = profileURL;
//    }
//
//
//}
