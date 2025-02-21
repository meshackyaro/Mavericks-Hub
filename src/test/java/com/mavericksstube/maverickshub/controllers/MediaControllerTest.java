package com.mavericksstube.maverickshub.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.mavericksstube.maverickshub.utils.TestUtils.TEST_IMAGE_LOCATION;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
@AutoConfigureMockMvc
@WithMockUser(authorities = {"USER"})
public class MediaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testMediaController() throws Exception {
        try(InputStream inputStream = Files.newInputStream(Paths.get(TEST_IMAGE_LOCATION))){
            MultipartFile file = new MockMultipartFile("mediaFile", inputStream);
            mvc.perform(multipart("/api/v1/media")
                    .file(file.getName(), file.getBytes())
                    .part(new MockPart("userId", "200".getBytes()))
                    .part(new MockPart("description", "test description".getBytes()))
                    .part(new MockPart("category", "HORROR".getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA))
                    .andExpect(status().isCreated()).andDo(print());
        } catch (Exception exception){
            throw exception;
        }
    }

    @Test
    public void testGetMediaUser(){
        try{
            mvc.perform(get("/api/v1/media?userId=200")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is2xxSuccessful()).andDo(print());
        } catch (Exception e){
            assertThat(e).isNull();
        }
    }

    @Test
    public void testGetMediaForUserShouldFailForInvalidUserId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/media?userId=2000")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(print());
    }

}
