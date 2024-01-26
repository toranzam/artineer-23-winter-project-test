package com.artineer.artineer23winterproject.controller;


import com.artineer.artineer23winterproject.auth.CurrentUser;
import com.artineer.artineer23winterproject.dto.StudyDto;
import com.artineer.artineer23winterproject.entity.Account;
import com.artineer.artineer23winterproject.entity.Study;
import com.artineer.artineer23winterproject.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring6.processor.SpringUErrorsTagProcessor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StudyController {

    private final StudyRepository studyRepository;

    @GetMapping("/study")
    public String showStudyPage(Model model) {

        List<Study> studies = studyRepository.findAll();

        model.addAttribute("studies", studies);

        return "/study/studies";
    }


    @GetMapping("/study/new")
    public String showNewStudyPage() {


        return "/study/newStudy";
    }

    @PostMapping("/study/new")
    public String CreateNewStudy(StudyDto studyDto,
                                 @CurrentUser Account account) {

        Study study = Study.builder()
                .title(studyDto.getTitle())
                .shortDesc(studyDto.getShortDesc())
                .fullDesc(studyDto.getFullDesc())
                .localDateTime(LocalDateTime.now())
                .manager(account)
                .published(true)
                .build();

        studyRepository.save(study);

        return "redirect:/study/" + study.getId();
    }

    @GetMapping("/study/{id}")
    public String showStudyDetail(@PathVariable("id") Long id,
                                  @CurrentUser Account account,
                                  Model model) {
        Study study = studyRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        model.addAttribute("study", study);
        model.addAttribute("isOwner", study.getManager().equals(account));

        return "/study/studyDetail";
    }

    @GetMapping("/study/{id}/member")
    public String showStudyMember(Model model, @PathVariable("id") Long id) {
        Study study = studyRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        model.addAttribute("study", study);

        return "/study/studyMember";
    }



}
