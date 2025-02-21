package com.mavericksstube.maverickshub.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditCommentRequest {
    private Long commentId;
    private Long commenterId;
    private String comment;
}
