//package com.example.AA.dto;
//
//import com.example.AA.entity.Designer;
//import com.example.AA.entity.FaceShape;
//import com.example.AA.entity.User;
//import lombok.Builder;
//import lombok.Getter;
//
//@Getter
//public class DesignerResDto {
//    private String nickname;
//    private String picture;
//    private String role;
//    private FaceShape faceShapeBest;
//    private FaceShape faceShapeWorst;
//    private String gender;
//    private String phoneNumber;
//    private String workplace;
//    private String snsAddress;
//    private String introduction;
//    private int likesCount;
//    private String profileURL;
//
//    // DesignerDTO를 Designer 엔티티로 변환하는 메서드
//    @ Builder
//    public DesignerResDto(User user, Designer designer) {
//        this.nickname = user.getNickname();
//        this.picture = user.getImage();
//        this.role = String.valueOf(user.getRole());
//        this.faceShapeBest = user.getFaceShapeBest();
//        this.faceShapeWorst = user.getFaceShapeWorst();
//        this.gender = designer.getGender();
//        this.phoneNumber = designer.getPhoneNumber();
//        this.workplace = designer.getWorkplace();
//        this.snsAddress = designer.getSnsAddress();
//        this.introduction = designer.getIntroduction();
//        this.likesCount = designer.getLikesCount();
//        this.profileURL = designer.getProfileURL();
//    }
//
//}
