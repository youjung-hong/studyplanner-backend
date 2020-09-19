package com.youjunghong.studyplannerapi.controller;

import com.youjunghong.studyplannerapi.domain.Subject;
import com.youjunghong.studyplannerapi.dto.SubjectReqDto;
import com.youjunghong.studyplannerapi.dto.SubjectResDto;
import com.youjunghong.studyplannerapi.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/subjects")
    public ResponseEntity<Page<SubjectResDto>> findAll(@PageableDefault Pageable pageable) {
        return new ResponseEntity<>(subjectService.findAll(pageable).map(SubjectResDto::toSubjectResDto), HttpStatus.OK);
    }

    @PostMapping("/subjects")
    public ResponseEntity<SubjectResDto> create(@RequestBody SubjectReqDto subjectReqDto) {
        Subject created = subjectService.save(Subject.builder()
                .title(subjectReqDto.getTitle())
                .build()
        );

        return new ResponseEntity<>(SubjectResDto.toSubjectResDto(created), HttpStatus.CREATED);
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<SubjectResDto> findOne(@PathVariable("id") Long id) {
        Optional<Subject> opt = subjectService.findOne(id);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(SubjectResDto.toSubjectResDto(opt.get()), HttpStatus.OK);
    }

    @PutMapping("/subjects/{id}")
    public ResponseEntity<SubjectResDto> update(@PathVariable("id") Long id, @RequestBody SubjectReqDto subjectReqDto) {
        Optional<Subject> opt = subjectService.findOne(id);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Subject found = opt.get();
        found.setTitle(subjectReqDto.getTitle());
        Subject updated = subjectService.save(found);

        return new ResponseEntity<>(SubjectResDto.toSubjectResDto(updated), HttpStatus.OK);
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Subject> opt = subjectService.findOne(id);

        if (!opt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        subjectService.delete(opt.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
