package com.example.AA.controller;

import com.example.AA.entity.User;
import com.example.AA.global.jwt.OAuthUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class RoleController {

    private final OAuthUserService oAuthUserService;

    @Autowired
    public RoleController(OAuthUserService oAuthUserService) {
        this.oAuthUserService = oAuthUserService;
    }
    @Operation(summary = "Role 선택")
    @PostMapping("/select-role")
    public ResponseEntity<?> selectRole(HttpServletRequest httpRequest, @RequestParam String role) {
        try {
            System.out.println("New Role: " + role);
            oAuthUserService.selectRole(httpRequest, role);
            // 업데이트 성공 시 응답
            return ResponseEntity.ok(Map.of("success", true, "message", "Role updated successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            // 업데이트 실패 시 응답
            return ResponseEntity.ok(Map.of("success", false, "error", e.getMessage()));
        }
    }
}
