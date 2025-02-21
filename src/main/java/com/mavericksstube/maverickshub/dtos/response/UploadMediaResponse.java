package com.mavericksstube.maverickshub.dtos.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.mavericksstube.maverickshub.models.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UploadMediaResponse {
    private Long id;
    @JsonProperty("media_url")
    private String url;
    @JsonProperty("media_description")
    private String description;
    private Category category;
}
