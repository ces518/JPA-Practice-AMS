package me.june.academy.domain.groups.service;

import lombok.RequiredArgsConstructor;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.groups.repository.GroupsRepository;
import me.june.academy.domain.groups.repository.GroupsSearch;
import me.june.academy.domain.groups.web.GroupsForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-03-18
 * Time: 13:56
 **/
@Service
@RequiredArgsConstructor
public class GroupsService {

    private final GroupsRepository groupsRepository;

    // 목록 조회
    public Page<Groups> findAll(GroupsSearch groupsSearch, Pageable pageable) {
        return groupsRepository.findAll(groupsSearch, pageable);
    }

    // 상세 조회
    public Groups findGroups(Long id) {
        Assert.notNull(id, "Groups id should be not null");
        return groupsRepository.findById(id)
                .orElseThrow(() -> new NotFoundGroupsException("존재하지 않는 반 입니다."));
    }

    // 등록
    public Long saveGroups(GroupsForm groupsForm) {
        Groups groups = groupsForm.toEntity();
        Groups savedGroups = groupsRepository.save(groups);
        return savedGroups.getId();
    }

    // 수정
    @Transactional
    public void updateGroups(GroupsForm groupsForm) {
        Groups groups = groupsForm.toEntity();
        Groups findGroups = findGroups(groupsForm.getId());
        findGroups.update(groups);
    }

    // 삭제
    @Transactional
    public void deleteGroups(Long id) {
        Groups findGroups = findGroups(id);
        findGroups.disable();
    }
}
