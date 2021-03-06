package com.youjunghong.studyplannerapi.service;

import com.youjunghong.studyplannerapi.domain.Subject;
import com.youjunghong.studyplannerapi.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public Page<Subject> findAll(Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("id").descending()
        );
        return subjectRepository.findAllByDeletedAtIsNull(pageable);
    }

    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Optional<Subject> findOne(Long id) {
        return subjectRepository.findById(id);
    }

    public void delete(Subject subject) {
        subject.setDeletedAt(LocalDateTime.now());
        this.save(subject);
    }
}
