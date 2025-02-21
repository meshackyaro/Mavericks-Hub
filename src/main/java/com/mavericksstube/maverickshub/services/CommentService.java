package com.mavericksstube.maverickshub.services;


import com.mavericksstube.maverickshub.dtos.requests.AddCommentRequest;
import com.mavericksstube.maverickshub.dtos.requests.DeleteCommentRequest;
import com.mavericksstube.maverickshub.dtos.requests.EditCommentRequest;
import com.mavericksstube.maverickshub.dtos.requests.ReplyCommentRequest;
import com.mavericksstube.maverickshub.dtos.response.AddCommentResponse;
import com.mavericksstube.maverickshub.dtos.response.EditCommentResponse;
import com.mavericksstube.maverickshub.models.Comment;

import java.util.Collection;
import java.util.List;

public interface CommentService {
    AddCommentResponse comment(AddCommentRequest addCommentRequest, Long mediaId);

    EditCommentResponse editComment(EditCommentRequest editCommentRequest);

    Long count();

    void deleteComment(DeleteCommentRequest deleteCommentRequest);

    Comment findCommentBy(Long commentId);

    AddCommentResponse replyComment(ReplyCommentRequest replyCommentRequest);

    List<Comment> getMediaComment(long mediaId);
}
