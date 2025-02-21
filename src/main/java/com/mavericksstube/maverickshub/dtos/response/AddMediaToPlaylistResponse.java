package com.mavericksstube.maverickshub.dtos.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class AddMediaToPlaylistResponse {

     private Long id;
     private String name;
     private String description;

     @JsonSerialize(using = LocalDateTimeSerializer.class)
     private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;

    private List<MediaResponse> media;

    private UserResponse uploader;
}
