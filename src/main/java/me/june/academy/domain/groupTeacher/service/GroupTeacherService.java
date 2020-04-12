package me.june.academy.domain.groupTeacher.service;

import lombok.RequiredArgsConstructor;
import me.june.academy.domain.groupMember.GroupMember;
import me.june.academy.domain.groupTeacher.GroupTeacher;
import me.june.academy.domain.groupTeacher.repository.GroupTeacherRepository;
import me.june.academy.domain.groups.Groups;
import me.june.academy.domain.groups.service.GroupsService;
import me.june.academy.domain.teacher.Teacher;
import me.june.academy.domain.teacher.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: june
 * Date: 2020-04-12
 * Time: 18:39
 **/
@Service
@RequiredArgsConstructor
public class GroupTeacherService {

    private final GroupTeacherRepository groupTeacherRepository;
    private final GroupsService groupsService;
    private final TeacherService teacherService;

    public Optional<GroupTeacher> findGroupTeacher(Long groupsId, Long teacherId) {
        Assert.notNull(groupsId, "Groups id should be not null");
        Assert.notNull(teacherId, "Teacher id should be not null");
        return groupTeacherRepository.findByGroupIdAndTeacherId(groupsId, teacherId);
    }

    // 등록
    @Transactional
    public Long saveGroupTeacher(Long groupId, Long teacherId) {
        Groups findGroups = groupsService.findGroups(groupId);
        Teacher findTeacher = teacherService.findTeacher(teacherId);

        findGroupTeacher(groupId, teacherId)
                .ifPresent(groupTeacher -> {
                    throw new DuplicateGroupTeacherException("이미 지정된 담임 선생님 입니다.");
                });

        GroupTeacher groupTeacher = new GroupTeacher(findGroups, findTeacher);
        GroupTeacher savedGroupTeacher = groupTeacherRepository.save(groupTeacher);
        return savedGroupTeacher.getId();
    }

    // 삭제
    @Transactional
    public void deleteGroupTeacher(Long groupsId, Long teacherId) {
        GroupTeacher findGroupTeacher = findGroupTeacher(groupsId, teacherId)
                .orElseThrow(() -> new NotFoundGroupTeacherException("존재하지 않는 담임 선생님 입니다."));
        groupTeacherRepository.delete(findGroupTeacher);
    }
}
