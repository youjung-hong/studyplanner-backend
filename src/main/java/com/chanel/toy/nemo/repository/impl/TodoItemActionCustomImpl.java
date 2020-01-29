package com.chanel.toy.nemo.repository.impl;

import com.chanel.toy.nemo.model.TodoItemAction;
import com.chanel.toy.nemo.repository.TodoItemActionCustom;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TodoItemActionCustomImpl implements TodoItemActionCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TodoItemAction> findActionsByTodoIdAndDate(Long todoId, LocalDate date) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TodoItemAction> query = cb.createQuery(TodoItemAction.class);
        Root<TodoItemAction> action = query.from(TodoItemAction.class);

        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(todoId)) {
            predicates.add(cb.equal(action.get("todoId"), todoId));
        }

        if (!ObjectUtils.isEmpty(date)) {
            LocalDateTime startOfThisDay = date.atStartOfDay();
            LocalDateTime startOfNextDay = date.plusDays(1).atStartOfDay();
            predicates.add(cb.or(
                cb.between(action.get("startAt"), startOfThisDay, startOfNextDay),
                cb.between(action.get("endAt"), startOfThisDay, startOfNextDay)
            ));
        }

        query.select(action)
                .where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query)
                .getResultList();
    }
}
