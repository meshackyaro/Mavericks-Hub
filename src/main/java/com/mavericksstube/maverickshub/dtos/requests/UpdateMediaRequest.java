package com.mavericksstube.maverickshub.dtos.requests;

import com.mavericksstube.maverickshub.models.Category;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateMediaRequest {
    private Long id;
    private String description;
    private Category category;
}
