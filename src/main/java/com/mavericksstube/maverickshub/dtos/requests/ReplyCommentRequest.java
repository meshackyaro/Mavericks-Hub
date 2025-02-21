package com.mavericksstube.maverickshub.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReplyCommentRequest {

    private Long commentId;
    private String replyContent;
    private Long replierId;
}
