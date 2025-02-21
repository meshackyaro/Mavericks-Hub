package com.mavericksstube.maverickshub.services;

import com.mavericksstube.maverickshub.dtos.requests.AddCommentRequest;
import com.mavericksstube.maverickshub.dtos.requests.DeleteCommentRequest;
import com.mavericksstube.maverickshub.dtos.requests.EditCommentRequest;
import com.mavericksstube.maverickshub.dtos.requests.ReplyCommentRequest;
import com.mavericksstube.maverickshub.dtos.response.AddCommentResponse;
import com.mavericksstube.maverickshub.dtos.response.EditCommentResponse;
import com.mavericksstube.maverickshub.dtos.response.UserResponse;
import com.mavericksstube.maverickshub.exceptions.CommentNotFoundException;
import com.mavericksstube.maverickshub.exceptions.UserNotFoundException;
import com.mavericksstube.maverickshub.exceptions.CommentCannotBeDeletedException;
import com.mavericksstube.maverickshub.models.Comment;
import com.mavericksstube.maverickshub.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MavericksHubCommentService implements CommentService {

    private UserService userService;
    private MediaService mediaService;
    private ModelMapper modelMapper;
    private CommentRepository commentRepository;


    @Override
    public AddCommentResponse comment(AddCommentRequest addCommentRequest, Long mediaId) {
        try{
            Comment comment = modelMapper.map(addCommentRequest, Comment.class);
            comment.setMedia(mediaService.getMediaBy(mediaId));
            comment = commentRepository.save(comment);
            UserResponse response = modelMapper.map(userService.getById(addCommentRequest.getUserId()), UserResponse.class);
            AddCommentResponse commentResponse = modelMapper.map(comment, AddCommentResponse.class);
            commentResponse.setUserResponse(response);
            return commentResponse;
        } catch (UserNotFoundException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public EditCommentResponse editComment(EditCommentRequest editCommentRequest) {
        Comment comment = findCommentBy(editCommentRequest.getCommentId());
        comment.setComment(editCommentRequest.getComment());
        commentRepository.save(comment);
        return modelMapper.map(comment, EditCommentResponse.class);
    }

    @Override
    public Long count() {
        return commentRepository.count();
    }

    @Override
    public void deleteComment(DeleteCommentRequest deleteCommentRequest) {
        Comment comment = findCommentBy(deleteCommentRequest.getCommentId());
        if(comment.getUserId().equals(deleteCommentRequest.getAccessorId()) ||
                comment.getMedia().getUploader().getId().equals(deleteCommentRequest.getAccessorId())) commentRepository.delete(comment);
        else throw new CommentCannotBeDeletedException(" Comment Can't Be Deleted By This User");
    }

    public Comment findCommentBy(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentNotFoundException("Comment not found"));
    }

    @Override
    public AddCommentResponse replyComment(ReplyCommentRequest replyCommentRequest) {
        Comment comment = findCommentBy(replyCommentRequest.getCommentId());
        Comment reply = new Comment();
        reply.setComment(replyCommentRequest.getReplyContent());
        reply.setUserId(replyCommentRequest.getReplierId());
        reply = commentRepository.save(reply);
        comment.getCommentReplies().add(reply);
        commentRepository.save(comment);
        return modelMapper.map(comment,AddCommentResponse.class);
    }

    @Override
    public List<Comment> getMediaComment(long mediaId) {
        return commentRepository.findCommentBy(mediaId);
    }
}
