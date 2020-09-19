package com.youjunghong.studyplannerapi.repository;

import com.youjunghong.studyplannerapi.domain.Subject;
import com.youjunghong.studyplannerapi.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Page<Subject> findAllByDeletedAtIsNull(Pageable pageable);
}
