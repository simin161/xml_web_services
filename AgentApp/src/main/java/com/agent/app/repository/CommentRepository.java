package com.agent.app.repository;

import com.agent.app.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comments WHERE job_offer=:id",nativeQuery = true)
    List<Comment> commentsByOfferId(@Param("id")Long id);


}
