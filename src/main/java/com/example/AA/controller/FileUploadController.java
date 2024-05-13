package com.example.AA.controller;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.AA.entity.Portfolio;
import com.example.AA.entity.User;
import com.example.AA.global.jwt.JwtTokenProvider;
import com.example.AA.global.s3.S3Service;
import com.example.AA.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {

	private final S3Service s3service;
    private final PortfolioRepository portfolioRepository;
    private final JwtTokenProvider jwtTokenProvider;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(HttpServletRequest httpRequest,
                                                    @RequestParam("files") List<MultipartFile> files) {
        try {
            List<String> preSignedUrls = new ArrayList<>();
            User user = jwtTokenProvider.getUserInfoByToken(httpRequest);
            Portfolio portfolio = portfolioRepository.findPortfolioByUser(user);

            for (MultipartFile file : files) {
                // 파일이 없으면 스킵
                if (file == null) {
                    continue;
                }

                String fileName = file.getOriginalFilename();

                // S3 Presigned URL 및 objectKey 생성
                Map<String, Serializable> presignedUrlInfo = s3service.getPreSignedUrl(httpRequest, fileName);
                String preSignedUrl = removeQueryString(presignedUrlInfo.get("preSignedUrl").toString());
                String objectKey = presignedUrlInfo.get("objectKey").toString();

                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType(file.getContentType());
                metadata.setContentLength(file.getSize());

                // S3에 파일 업로드
                s3service.uploadFileToS3(objectKey, file, metadata);
                preSignedUrls.add(preSignedUrl);
            }

            // 업로드된 파일의 개수만큼 Presigned URL을 사용하여 포트폴리오에 이미지 URL을 저장
            switch (preSignedUrls.size()) {
                case 4:
                    portfolio.uploadImageUrls(preSignedUrls.get(0), preSignedUrls.get(1), preSignedUrls.get(2), preSignedUrls.get(3));
                    break;
                case 3:
                    portfolio.uploadImageUrls(preSignedUrls.get(0), preSignedUrls.get(1), preSignedUrls.get(2), null);
                    break;
                case 2:
                    portfolio.uploadImageUrls(preSignedUrls.get(0), preSignedUrls.get(1), null, null);
                    break;
                case 1:
                    portfolio.uploadImageUrls(preSignedUrls.get(0), null, null, null);
                    break;
                default:
                    break;
            }

            portfolioRepository.save(portfolio);
            return ResponseEntity.ok(preSignedUrls);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    private String removeQueryString(String url) {
		int index = url.indexOf("?");
		return (index == -1) ? url : url.substring(0, index);
	}

}
