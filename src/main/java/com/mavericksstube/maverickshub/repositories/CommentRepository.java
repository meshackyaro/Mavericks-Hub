package com.mavericksstube.maverickshub.repositories;

import com.mavericksstube.maverickshub.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT co FROM Comment co WHERE co.media.id=:mediaId")
    List<Comment> findCommentBy(long mediaId);
}
