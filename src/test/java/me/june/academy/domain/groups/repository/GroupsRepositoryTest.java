package me.june.academy.domain.groups.repository;

import me.june.academy.domain.groups.Groups;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class GroupsRepositoryTest {

    @Autowired GroupsRepository groupsRepository;

    @BeforeEach
    public void before() {
        groupsRepository.save(new Groups("groupA"));
        groupsRepository.save(new Groups("groupB"));
        groupsRepository.save(new Groups("groupC"));
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