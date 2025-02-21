package com.mavericksstube.maverickshub.services;

import com.mavericksstube.maverickshub.dtos.requests.AddCommentRequest;
import com.mavericksstube.maverickshub.dtos.requests.DeleteCommentRequest;
import com.mavericksstube.maverickshub.dtos.requests.EditCommentRequest;
import com.mavericksstube.maverickshub.dtos.requests.ReplyCommentRequest;
import com.mavericksstube.maverickshub.dtos.response.AddCommentResponse;
import com.mavericksstube.maverickshub.dtos.response.EditCommentResponse;
import com.mavericksstube.maverickshub.models.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void addCommentToMedia(){
        AddCommentRequest addCommentRequest = new AddCommentRequest();
        addCommentRequest.setUserId(200L);
        addCommentRequest.setComment("comment to media");

        AddCommentResponse response = commentService.comment(addCommentRequest, 100L);
        assertThat(response).isNotNull();
        assertThat(response.getComment()).containsIgnoringCase("comment");
    }

    @Test
    public void addCommentCanBeEdited(){
        AddCommentRequest addCommentRequest = new AddCommentRequest();
        addCommentRequest.setUserId(200L);
        addCommentRequest.setComment("comment to media");
        AddCommentResponse response = commentService.comment(addCommentRequest, 100L);
        assertThat(response.getComment()).containsIgnoringCase("comment");

        EditCommentRequest editCommentRequest = new EditCommentRequest();
        editCommentRequest.setCommentId(response.getCommentId());
        editCommentRequest.setCommenterId(203L);
        editCommentRequest.setComment("updated comment to media");
        EditCommentResponse editCommentResponse = commentService.editComment(editCommentRequest);
        assertThat(editCommentResponse).isNotNull();
        assertThat(editCommentResponse.getComment()).containsIgnoringCase("update");
    }

    @Test
    public void deleteCommentFromMedia(){
        AddCommentRequest addCommentRequest = new AddCommentRequest();
        addCommentRequest.setUserId(200L);
        addCommentRequest.setComment("comment to media");
        AddCommentResponse response = commentService.comment(addCommentRequest, 100L);
        assertThat(response.getComment()).containsIgnoringCase("comment");
        assertThat(commentService.count()).isEqualTo(1L);

        DeleteCommentRequest deleteCommentRequest = new DeleteCommentRequest();
        deleteCommentRequest.setCommentId(response.getCommentId());
        deleteCommentRequest.setAccessorId(200L);
        commentService.deleteComment(deleteCommentRequest);

        assertThat(commentService.count()).isEqualTo(0);
    }

    @Test
    public void commentCanBeReplied(){
        AddCommentRequest request = new AddCommentRequest();
        request.setUserId(200L);
        request.setComment("comment");
        AddCommentResponse response = commentService.comment(request,100L);
        assertThat(response.getComment()).isEqualTo("comment");
        assertThat(commentService.count()).isEqualTo(1);
        Comment comment = commentService.findCommentBy(response.getCommentId());

        ReplyCommentRequest replyCommentRequest = new ReplyCommentRequest();
        replyCommentRequest.setReplyContent("reply content");
        replyCommentRequest.setReplierId(201L);
        replyCommentRequest.setCommentId(response.getCommentId());
        assertThat(comment.getCommentReplies().size()).isEqualTo(0);

        commentService.replyComment(replyCommentRequest);
        comment = commentService.findCommentBy(response.getCommentId());

        assertThat(comment.getCommentReplies().size()).isEqualTo(1);
    }

    @Test
    public void testThatMediaCanGetAllComment(){

        assertThat(commentService.getMediaComment(100L).size()).isEqualTo(0);
        AddCommentRequest request = new AddCommentRequest();
        request.setUserId(200L);

        request.setComment("comment");
        commentService.comment(request,100L);
        assertThat(commentService.getMediaComment(100L).size()).isEqualTo(1);
    }
    @Test
    public void testMediaOwnerCanDeletedUnwantedComment() {
        AddCommentRequest request = new AddCommentRequest();
        request.setUserId(200L);

        request.setComment("comment");
        AddCommentResponse response = commentService.comment(request,102L);
        assertThat(commentService.count()).isEqualTo(1);

        DeleteCommentRequest deleteCommentRequest = new DeleteCommentRequest();
        deleteCommentRequest.setCommentId(response.getCommentId());
        deleteCommentRequest.setAccessorId(200L);
        commentService.deleteComment(deleteCommentRequest);

        assertThat(commentService.count()).isEqualTo(0);
    }
}
