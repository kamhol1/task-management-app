package com.example.taskmanagementapp.specification;

import com.example.taskmanagementapp.model.Status;
import com.example.taskmanagementapp.model.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification implements Specification<Task> {

    private final List<SearchCriteria> searchCriteriaList = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        searchCriteriaList.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : searchCriteriaList) {
            String key = criteria.getKey();
            Object value = criteria.getValue();

            switch (key) {
                case "id" -> predicates.add(
                        criteriaBuilder.equal(root.get(key), value)
                );
                case "title" -> predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(key)), "%" + value.toString().toLowerCase() + "%"
                        )
                );
                case "user" -> {
                    if (!value.toString().isEmpty()) {
                        predicates.add(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get(key).get("username")), "%" + value.toString().toLowerCase() + "%"
                                )
                        );
                    }
                }
                case "status", "priority" -> predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(key).as(String.class)), "%" + value.toString().toLowerCase() + "%"
                        )
                );
                case "excludeCompletedAndCancelled" -> {
                    if (value.equals(true)) {
                        predicates.add(
                                criteriaBuilder.notEqual(
                                        criteriaBuilder.lower(root.get("status").as(String.class)), Status.COMPLETED.toString().toLowerCase()
                                )
                        );
                        predicates.add(
                                criteriaBuilder.notEqual(
                                        criteriaBuilder.lower(root.get("status").as(String.class)), Status.CANCELLED.toString().toLowerCase()
                                )
                        );
                    }
                }
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
