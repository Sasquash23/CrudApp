package com.crud.tasks.repository;

import com.crud.tasks.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Override
    List<Task> findAll();

    @Autowired
    Task save(Task task);
}
