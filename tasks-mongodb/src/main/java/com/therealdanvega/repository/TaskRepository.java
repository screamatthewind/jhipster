package com.therealdanvega.repository;

import com.therealdanvega.domain.Task;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

}
