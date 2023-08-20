package com.example.taskmanagementapp.specification;

import com.example.taskmanagementapp.model.StatusEnum;
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
            if (criteria.getKey().equals("id")) {
                predicates.add(
                        criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue())
                );
            }
            if (criteria.getKey().equals("title")) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"
                        )
                );
            }
            if (criteria.getKey().equals("status") || criteria.getKey().equals("priority")) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get(criteria.getKey()).as(String.class)), "%" + criteria.getValue().toString().toLowerCase() + "%"
                        )
                );
            }
            if (criteria.getKey().equals("excludeCompletedAndCancelled") && criteria.getValue().equals(true)) {
                predicates.add(
                        criteriaBuilder.notEqual(
                                criteriaBuilder.lower(root.get("status").as(String.class)), StatusEnum.COMPLETED.toString().toLowerCase()
                        )
                );
                predicates.add(
                        criteriaBuilder.notEqual(
                                criteriaBuilder.lower(root.get("status").as(String.class)), StatusEnum.CANCELLED.toString().toLowerCase()
                        )
                );
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
