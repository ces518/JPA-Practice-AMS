package me.june.academy.domain.groups.service;

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
    
    @Test
    public void 반_목록_조회() throws Exception {
        // given
        groupsRepository.save(new Groups("groupA"));
        groupsRepository.save(new Groups("groupB"));
        groupsRepository.save(new Groups("groupC"));

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
        groupsRepository.save(new Groups("groupA"));
        groupsRepository.save(new Groups("groupB"));
        groupsRepository.save(new Groups("groupC"));

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
        GroupsForm groupA = new GroupsForm("groupA");

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
        Groups savedGroups = groupsRepository.save(new Groups("groupA"));
        Long savedGroupsId = savedGroups.getId();

        // when
        Groups findGroups = groupsService.findGroups(savedGroupsId);

        // then
        assertThat(findGroups.getName()).isEqualTo("groupA");
    }
    
    @Test
    public void 반_상세_조회_실패_존재하지_않는_반() throws Exception {
        // given
        Groups savedGroups = groupsRepository.save(new Groups("groupA"));
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
        Groups savedGroups = groupsRepository.save(new Groups("groupA"));
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
        Groups savedGroups = groupsRepository.save(new Groups("groupA"));
        Long savedGroupsId = savedGroups.getId();

        GroupsForm form = new GroupsForm("groupA수정");
        form.setId(savedGroupsId);

        // when
        groupsService.updateGroups(form);
        Groups findGroups = groupsService.findGroups(savedGroupsId);

        // then
        assertThat(findGroups.getName()).isEqualTo("groupA수정");
    }

    @Test
    public void 반_수정_실패_존재하지_않는_반() throws Exception {
        // given
        Groups savedGroups = groupsRepository.save(new Groups("groupA"));
        Long savedGroupsId = savedGroups.getId();

        GroupsForm form = new GroupsForm("groupA수정");
        form.setId(savedGroupsId + 1);

        // when
        NotFoundGroupsException exception = Assertions.assertThrows(NotFoundGroupsException.class, () -> {
            groupsService.updateGroups(form);
        });

        // then
        assertThat(exception.getMessage()).isEqualTo("groupA수정");
    }
}