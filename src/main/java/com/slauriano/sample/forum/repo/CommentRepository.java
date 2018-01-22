package com.slauriano.sample.forum.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slauriano.sample.forum.model.Comment;
import com.slauriano.sample.forum.model.Topic;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findCommentsByTopic(Topic topic);
	
}
