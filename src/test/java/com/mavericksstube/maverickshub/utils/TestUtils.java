package com.mavericksstube.maverickshub.utils;

import com.mavericksstube.maverickshub.dtos.requests.UploadMediaRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.mavericksstube.maverickshub.models.Category.ACTION;

public class TestUtils {
    public static final String TEST_IMAGE_LOCATION = "C:\\Users\\DELL\\IdeaProjects\\mavericks-hub\\src\\main\\resources\\static\\groot.jpg";
    public static final String TEST_VIDEO_LOCATION = "C:\\Users\\DELL\\IdeaProjects\\mavericks-hub\\src\\main\\resources\\static\\vid.mp4";

    public static UploadMediaRequest buildUploadRequest(InputStream inputStream) throws IOException {
        UploadMediaRequest uploadMediaRequest = new UploadMediaRequest();
        MultipartFile file = new MockMultipartFile("Video", inputStream);
        uploadMediaRequest.setMediaFile(file);
        uploadMediaRequest.setCategory(ACTION);
        uploadMediaRequest.setUserId(201L);
        return uploadMediaRequest;
    }

}
