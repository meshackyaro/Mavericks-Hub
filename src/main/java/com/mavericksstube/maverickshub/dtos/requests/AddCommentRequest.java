package com.mavericksstube.maverickshub.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddCommentRequest {
    private Long userId;
    private String comment;
}
