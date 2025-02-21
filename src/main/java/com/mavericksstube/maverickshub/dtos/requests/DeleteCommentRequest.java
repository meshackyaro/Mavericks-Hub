package com.mavericksstube.maverickshub.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteCommentRequest {

    private Long commentId;
    private Long accessorId;
}
