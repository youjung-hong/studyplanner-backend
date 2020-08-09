package com.youjunghong.studyplannerapi.repository;

import com.youjunghong.studyplannerapi.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
