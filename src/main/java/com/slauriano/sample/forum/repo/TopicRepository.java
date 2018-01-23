package com.slauriano.sample.forum.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.slauriano.sample.forum.model.Topic;



public interface TopicRepository extends JpaRepository<Topic, Long> {

	@Query("Select t from Topic t where t.title LIKE %?1%")
    List<Topic> findByTitle(String title);
    
    Topic findById(Long id);
}
