package com.youjunghong.studyplannerapi.dto;

import com.youjunghong.studyplannerapi.domain.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectResDto {
    Long id;
    String title;

    public static SubjectResDto toSubjectResDto(Subject subject) {
        return new SubjectResDto(subject.getId(), subject.getTitle());
    }
}
