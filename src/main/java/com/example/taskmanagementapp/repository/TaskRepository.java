package com.example.taskmanagementapp.repository;

import com.example.taskmanagementapp.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("select t from Task t where t.status != 'COMPLETED' AND t.status != 'CANCELLED'")
    Page<Task> findAllActiveTasks(Pageable page);

    Page<Task> findAll(Pageable page);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "category",
                    "notes"
            }
    )
    Optional<Task> findById(int id);
}
