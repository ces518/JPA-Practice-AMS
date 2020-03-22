package me.june.academy.domain.groups.repository;

import me.june.academy.domain.grade.Grade;
import me.june.academy.domain.grade.repository.GradeRepository;
import me.june.academy.domain.groupMember.GroupMember;
import me.june.academy.domain.groupMember.repository.GroupMemberRepository;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.member.Member;
import me.june.academy.domain.member.repository.MemberRepository;
import me.june.academy.model.Address;
import org.junit.jupiter.api.BeforeEach;
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
class GroupsRepositoryTest {

    @Autowired GroupsRepository groupsRepository;
    @Autowired GradeRepository gradeRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired GroupMemberRepository groupMemberRepository;

    @BeforeEach
    public void before() {
        Grade savedGrade = gradeRepository.save(new Grade("gradeA"));
        groupsRepository.save(new Groups("groupA", savedGrade));
        groupsRepository.save(new Groups("groupB", savedGrade));
        groupsRepository.save(new Groups("groupC", savedGrade));
    }

    @Test
    public void findGroupsById() throws Exception {
        // given
        Grade savedGrade = gradeRepository.save(new Grade("gradeA1"));
        Groups savedGroups = groupsRepository.save(new Groups("groupA1", savedGrade));

        // when
        Optional<Groups> optionalGrade = groupsRepository.findWithGradeById(savedGroups.getId());
        Groups findGroups = optionalGrade.get();

        // then
        assertThat(findGroups).isEqualTo(savedGroups);
        assertThat(findGroups.getGrade()).isEqualTo(savedGrade);
        assertThat(findGroups.getGrade().getName()).isEqualTo(savedGrade.getName());
    }

    @Test
    public void findAll_no_search() throws Exception {
        // given
        GroupsSearch groupsSearch = new GroupsSearch();
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Groups> page = groupsRepository.findAll(groupsSearch, pageRequest);
        List<Groups> groups = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(3);
        assertThat(groups.size()).isEqualTo(3);
    }

    @Test
    public void findAll_search() throws Exception {
        // given
        GroupsSearch groupsSearch = new GroupsSearch("groupB");
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<Groups> page = groupsRepository.findAll(groupsSearch, pageRequest);
        List<Groups> groups = page.getContent();
        long totalCount = page.getTotalElements();

        // then
        assertThat(totalCount).isEqualTo(1);
        assertThat(groups.size()).isEqualTo(1);
        assertThat(groups.get(0).getName()).isEqualTo("groupB");
    }
}