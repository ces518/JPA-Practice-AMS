package me.june.academy.domain.groups.service;

import me.june.academy.common.BadRequestException;
import me.june.academy.domain.grade.Grade;
import me.june.academy.domain.grade.repository.GradeRepository;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.groups.repository.GroupsRepository;
import me.june.academy.domain.groups.repository.GroupsSearch;
import me.june.academy.domain.groups.web.GroupsForm;
import me.june.academy.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class GroupsServiceTest {

    @Autowired GroupsService groupsService;
    @Autowired GroupsRepository groupsRepository;
    @Autowired GradeRepository gradeRepository;
    
    @Test
    public void 반_목록_조회() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        groupsRepository.save(new Groups("groupA", savedGrade));
        groupsRepository.save(new Groups("groupB", savedGrade));
        groupsRepository.save(new Groups("groupC", savedGrade));

        GroupsSearch groupsSearch = new GroupsSearch();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Groups> page = groupsService.findAll(groupsSearch, pageRequest);
        List<Groups> groups = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(groups.size()).isEqualTo(3);
    }

    @Test
    public void 반_목록_조회_groupB검색() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        groupsRepository.save(new Groups("groupA", savedGrade));
        groupsRepository.save(new Groups("groupB", savedGrade));
        groupsRepository.save(new Groups("groupC", savedGrade));

        GroupsSearch groupsSearch = new GroupsSearch("groupB");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Groups> page = groupsService.findAll(groupsSearch, pageRequest);
        List<Groups> groups = page.getContent();
        long totalCount = page.getTotalElements();


        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(groups.size()).isEqualTo(1);
        assertThat(groups.get(0).getName()).isEqualTo("groupB");
    }
    
    @Test
    public void 반_생성() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        GroupsForm groupA = new GroupsForm("groupA", savedGrade.getId());

        // when
        Long savedGroupsId = groupsService.saveGroups(groupA);
        Optional<Groups> optionalGroups = groupsRepository.findById(savedGroupsId);
        Groups findGroups = optionalGroups.get();

        // then
        assertThat(findGroups.getName()).isEqualTo("groupA");
        assertThat(findGroups.getStatus()).isEqualTo(Status.AVAILABLE);
    }
    
    @Test
    public void 반_상세_조회_성공() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroups = groupsRepository.save(new Groups("groupA", savedGrade));
        Long savedGroupsId = savedGroups.getId();

        // when
        Groups findGroups = groupsService.findGroups(savedGroupsId);

        // then
        assertThat(findGroups.getName()).isEqualTo("groupA");
    }
    
    @Test
    public void 반_상세_조회_실패_존재하지_않는_반() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroups = groupsRepository.save(new Groups("groupA", savedGrade));
        Long savedGroupsId = savedGroups.getId();

        // when
        NotFoundGroupsException exception = Assertions.assertThrows(NotFoundGroupsException.class, () -> {
            groupsService.findGroups(savedGroupsId + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 반 입니다.");
    }

    @Test
    public void 반_상세_조회_실패_id값_null() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroups = groupsRepository.save(new Groups("groupA", savedGrade));
        Long savedGroupsId = savedGroups.getId();

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            groupsService.findGroups(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Groups id should be not null");
    }

    @Test
    public void 반_수정_성공() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroups = groupsRepository.save(new Groups("groupA", savedGrade));
        Long savedGroupsId = savedGroups.getId();

        GroupsForm form = new GroupsForm("groupA수정", savedGrade.getId());
        form.setId(savedGroupsId);

        // when
        groupsService.updateGroups(form);
        Optional<Groups> optionalGroups = groupsRepository.findById(savedGroupsId);
        Groups findGroups = optionalGroups.get();

        // then
        assertThat(findGroups.getName()).isEqualTo("groupA수정");
    }

    @Test
    public void 반_수정_실패_존재하지_않는_반() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroups = groupsRepository.save(new Groups("groupA", savedGrade));
        Long savedGroupsId = savedGroups.getId();

        GroupsForm form = new GroupsForm("groupA수정", savedGrade.getId());
        form.setId(savedGroupsId + 1);

        // when
        NotFoundGroupsException exception = Assertions.assertThrows(NotFoundGroupsException.class, () -> {
            groupsService.updateGroups(form);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 반 입니다.");
    }
    
    @Test
    public void 반_수정_실패_id값_null() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroups = groupsRepository.save(new Groups("groupA", savedGrade));
        Long savedGroupsId = savedGroups.getId();

        GroupsForm form = new GroupsForm("groupA수정", savedGrade.getId());
        form.setId(null);

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            groupsService.updateGroups(form);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Groups id should be not null");
    }
    
    @Test
    public void 반_수정_실패_status_disabled() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups groups = new Groups("groupA", savedGrade);
        groups.disable();
        Groups savedGroups = groupsRepository.save(groups);

        Long savedGroupsId = savedGroups.getId();

        GroupsForm form = new GroupsForm("groupA수정", savedGrade.getId());
        form.setId(savedGroupsId);

        // when
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            groupsService.updateGroups(form);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("비활성화 상태인 반의 정보는 수정할 수 없습니다.");
    }
    
    @Test
    public void 반_삭제_성공() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroups = groupsRepository.save(new Groups("groupA", savedGrade));
        Long savedGroupsId = savedGroups.getId();
        
        // when
        groupsService.deleteGroups(savedGroupsId);
        Optional<Groups> optionalGroups = groupsRepository.findById(savedGroupsId);
        Groups findGroups = optionalGroups.get();

        // then
        assertThat(findGroups.getStatus()).isEqualTo(Status.DISABLED);
    }

    @Test
    public void 반_삭제_실패_존재하지_않는_반() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroups = groupsRepository.save(new Groups("groupA", savedGrade));
        Long savedGroupsId = savedGroups.getId();

        // when
        NotFoundGroupsException exception = Assertions.assertThrows(NotFoundGroupsException.class, () -> {
            groupsService.deleteGroups(savedGroupsId + 1);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("존재하지 않는 반 입니다.");
    }
    
    @Test
    public void 반_삭제_실패_id값_null() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups savedGroups = groupsRepository.save(new Groups("groupA", savedGrade));
        Long savedGroupsId = savedGroups.getId();

        // when
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            groupsService.deleteGroups(null);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("Groups id should be not null");
    }
    
    @Test
    public void 반_삭제_실패_status_disabled() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        Groups groups = new Groups("groupA", savedGrade);
        groups.disable();
        Groups savedGroups = groupsRepository.save(groups);

        Long savedGroupsId = savedGroups.getId();

        // when
        BadRequestException exception = Assertions.assertThrows(BadRequestException.class, () -> {
            groupsService.deleteGroups(savedGroupsId);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("이미 비활성화된 반 입니다.");
    }
}