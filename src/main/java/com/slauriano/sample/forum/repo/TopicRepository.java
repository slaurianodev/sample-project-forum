package com.slauriano.sample.forum.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slauriano.sample.forum.model.Topic;



public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findByTitle(String title);
    Topic findById(Long id);
}
