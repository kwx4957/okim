package com.goorm.okim.presentation;

import com.goorm.okim.service.AWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class AWSS3Controller {
    private final AWSService awsService;
    @PostMapping("/uploads")
    public ResponseEntity<Object> uploadFiles(
            @RequestParam(value = "fileType") String fileType,
            @RequestPart(value = "file") MultipartFile multipartFiles) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(awsService.uploadFiles(multipartFiles));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteFile(
            @RequestParam(value = "uploadFilePath") String uploadFilePath,
            @RequestParam(value = "uuidFileName") String uuidFileName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(awsService.deleteFile(uploadFilePath, uuidFileName));
    }

}
