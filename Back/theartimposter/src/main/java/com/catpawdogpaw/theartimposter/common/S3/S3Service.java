package com.catpawdogpaw.theartimposter.common.S3;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
    @Autowired
	private AmazonS3 s3Client;
	
	@Value("${aws.bucketname}")
	private String bucketName;
	@Value("${aws.cloudfront.domain}")
    private String cloudfrontDomain;

	private static final String UPLOAD_PATH = "image/";
	
    public String uploadImage(MultipartFile file) {
		
        // 확장자 추출
    	String originalFileName = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid.toString() + fileExtension;
        
        //파일경로: 업로드폴더 + uuid.확장자
		String filePath = UPLOAD_PATH + savedFileName;
		try {
			s3Client.putObject(new PutObjectRequest(bucketName, filePath, file.getInputStream(), null));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String url = "https://" + cloudfrontDomain + "/" + filePath;
		
		return url;
    }
}