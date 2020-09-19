package com.youjunghong.studyplannerapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youjunghong.studyplannerapi.domain.Subject;
import com.youjunghong.studyplannerapi.dto.SubjectResDto;
import com.youjunghong.studyplannerapi.service.SubjectService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @Test
    public void test빈_과목_목록_조회() throws Exception {
        given(subjectService.findAll(PageRequest.of(0, 10)))
                .willReturn(new PageImpl<>(new ArrayList<>()));

        final ResultActions actions = this.mockMvc.perform(get("/subjects")
            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.empty", is(true)))
                .andExpect(jsonPath("$.totalElements", is(0)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andDo(print());
    }

    @Test
    public void 과목조회() throws Exception {
        given(subjectService.findOne(1L))
                .willReturn(Optional.of(new Subject(1L, "과목1")));

        final ResultActions actions = this.mockMvc.perform(get("/subjects/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("과목1")))
                .andDo(print());
    }

    @Test
    public void 과목생성() throws Exception {
        given(subjectService.save(any(Subject.class)))
                .willReturn(new Subject(1L, "과목1"));

        final ResultActions actions = this.mockMvc.perform(post("/subjects")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("-")
                .content("{ \"title\": \"과목1\" }"))
                .andDo(print());

        actions.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("과목1")))
                .andDo(print());
    }

    @Test
    public void 과목수정() throws Exception {
        given(subjectService.findOne(1L))
                .willReturn(Optional.of(new Subject(1L, "과목1")));
        given(subjectService.save(any(Subject.class)))
                .willReturn(new Subject(1L, "과목2"));

        final ResultActions actions = this.mockMvc.perform(put("/subjects/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\": 1, \"title\": \"과목2\" }"))
                .andDo(print());

        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("과목2")))
                .andDo(print());
    }

    @Test
    public void 과목삭제() throws Exception {
        given(subjectService.findOne(1L))
                .willReturn(Optional.of(new Subject(1L, "과목1")));

        final ResultActions actions = this.mockMvc.perform(delete("/subjects/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions.andExpect(status().isOk())
                .andDo(print());
    }
}
