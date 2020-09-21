package com.youjunghong.studyplannerapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youjunghong.studyplannerapi.domain.Subject;
import com.youjunghong.studyplannerapi.dto.SubjectReqDto;
import com.youjunghong.studyplannerapi.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.StringWriter;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubjectIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }

    @AutoConfigureMockMvc
    @Test
    public void contextLoads() {
    }

//    @Test @todo
    void test빈_목록을_조회할_수_있다() throws Exception {
        mockMvc.perform(get("/subjects")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.empty", is(true)))
                .andExpect(jsonPath("$.totalElements", is(0)))
                .andExpect(jsonPath("$.totalPages", is(0)))
                .andDo(print());

        Page<Subject> subjectPage = subjectRepository.findAllByDeletedAtIsNull(PageRequest.of(0, 10));
        assertThat(subjectPage.isEmpty(), is(true));
        assertThat(subjectPage.getTotalElements(), is(0L));
        assertThat(subjectPage.getTotalPages(), is(0L));
    }

//    @Test @todo
    void test과목을_생성할_수_있다() throws Exception {
        StringWriter sw = new StringWriter();
        SubjectReqDto subjectReqDto = SubjectReqDto.builder().title("수학").build();
        objectMapper.writer().writeValue(sw, subjectReqDto);

        Page<Subject> subjectPage = subjectRepository.findAllByDeletedAtIsNull(PageRequest.of(0, 10));
        assertThat(subjectPage.isEmpty(), is(true));

        mockMvc.perform(post("/subjects")
                .contentType("application/json")
                .content(sw.toString()))
                .andExpect(status().isCreated())
                .andDo(print());

        subjectPage = subjectRepository.findAllByDeletedAtIsNull(PageRequest.of(0, 10));
        assertThat(subjectPage.isEmpty(), is(false));
        assertThat(subjectPage.getTotalElements(), is(1));
        assertThat(subjectPage.getContent().get(0).getTitle(), is("수학"));
    }
}
