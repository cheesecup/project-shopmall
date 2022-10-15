package com.shop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    /* 파일 업로드 서비스 로직 */
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 확장자명 자르기

        String savedFileName = uuid.toString() + extension; //이미지 파일 저장소에 저장되는 파일명
        String fileUploadFullUrl = uploadPath + "/" + savedFileName; //저장 경로와 저장되는 파일명

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();

        return savedFileName;
    }

    /* 파일 삭제 서비스 로직*/
    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
