package com.mavericksstube.maverickshub.services;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.mavericksstube.maverickshub.dtos.requests.UpdateMediaRequest;
import com.mavericksstube.maverickshub.dtos.requests.UploadMediaRequest;
import com.mavericksstube.maverickshub.dtos.response.MediaResponse;
import com.mavericksstube.maverickshub.dtos.response.UpdateMediaResponse;
import com.mavericksstube.maverickshub.dtos.response.UploadMediaResponse;
import com.mavericksstube.maverickshub.exceptions.UserNotFoundException;
import com.mavericksstube.maverickshub.models.Category;
import com.mavericksstube.maverickshub.models.Media;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.mavericksstube.maverickshub.models.Category.*;
import static com.mavericksstube.maverickshub.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/data.sql"})
public class MediaServiceTest {

    @Autowired
    private MediaService mediaService;

    @Test
    @DisplayName("test you can upload images")
    public void uploadMediaTest(){
        Path path = Paths.get(TEST_IMAGE_LOCATION);

        try(var inputStream = Files.newInputStream(path);){
            UploadMediaRequest uploadMediaRequest = buildUploadRequest(inputStream);
            UploadMediaResponse response = mediaService.upload(uploadMediaRequest);

            log.info("response --> {}", response);
            assertThat(response).isNotNull();
            assertThat(response.getId()).isNotNull();
        } catch (IOException e) {
            assertThat(e).isNotNull();
        }
    }

    @Test
    @DisplayName("Test you can upload videos")
    public void uploadVideoTest(){
        Path path = Paths.get(TEST_VIDEO_LOCATION);

        try(var inputStream = Files.newInputStream(path);){
            UploadMediaRequest uploadMediaRequest = buildUploadRequest(inputStream);
            UploadMediaResponse response = mediaService.upload(uploadMediaRequest);

            assertThat(response).isNotNull();
            assertThat(response.getId()).isNotNull();
        } catch (IOException e){
            assertThat(e).isNotNull();
        }
    }

    @Test
    @DisplayName("test you can get media")
    public void getMediaByIdTest(){
        Media media = mediaService.getMediaBy(100L);
        log.info("found content -> {}", media);
        assertThat(media).isNotNull();
    }


    @Test
    @DisplayName("Test that media can be updated without setting the rest null")
    public void updateMediaTest(){
        Category category = mediaService.getMediaBy(100L).getCategory();
        assertThat(category).isEqualTo(ACTION);

        try{
            List<JsonPatchOperation> operations = List.of(new ReplaceOperation(new JsonPointer("/category"), new TextNode(ROMANCE.name())));
            JsonPatch updateMediaRequest = new JsonPatch(operations);
            UpdateMediaResponse response = mediaService.updateMedia(100L, updateMediaRequest);
            assertThat(response).isNotNull();
            category = mediaService.getMediaBy(100L).getCategory();
            assertThat(category).isEqualTo(ROMANCE);
        } catch (JsonPointerException e){
            assertThat(e).isNotNull();
        }
    }

    @Test
    public void getMediaForUserTest() throws UserNotFoundException {
        Long userId = 200L;
        List<MediaResponse> media = mediaService.getMediaFor(userId);
        assertThat(media).hasSize(3);
    }
}

