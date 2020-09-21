package com.youjunghong.studyplannerapi.repository.custom;

import com.youjunghong.studyplannerapi.domain.Task;
import com.youjunghong.studyplannerapi.domain.TaskTimeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class TaskTimeLogCustomRepositoryImpl implements TaskTimeLogCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<TaskTimeLog> findAllByDate(LocalDate date) {

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        TypedQuery<TaskTimeLog> query = entityManager.createQuery("" +
                "SELECT ttl FROM TaskTimeLog ttl WHERE " +
                " (ttl.startAt >= :startOfDay AND ttl.startAt < :endOfDay) OR " +
                " (ttl.endAt >= :startOfDay AND ttl.endAt < :endOfDay)", TaskTimeLog.class);
        query.setParameter("startOfDay", startOfDay);
        query.setParameter("endOfDay", endOfDay);
        List<TaskTimeLog> list = query.getResultList();

        return list;
    }
}
