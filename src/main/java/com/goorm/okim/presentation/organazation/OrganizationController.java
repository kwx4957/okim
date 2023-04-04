package com.goorm.okim.presentation.organazation;

import com.goorm.okim.common.Response;
import com.goorm.okim.infra.repository.OrganizationRepository;
import com.goorm.okim.service.OrganizationService;
import com.goorm.okim.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationRepository organizationRepository;
    private final OrganizationService organizationService;
    private final TaskService taskService;

    @GetMapping("/groupname")
    public ResponseEntity<?> getListGroupName(){
        return Response.success(organizationRepository.findAll());
    }


    @GetMapping("/groups/{groupId}/tasks")
    public ResponseEntity<?> getGroupTasks(@PathVariable long groupId, Pageable pageable) {
        return Response.success(taskService.getGroupTasks(groupId, pageable));
    }

    @GetMapping("/groups")
    public ResponseEntity<?> getGroups() {
        return Response.success(organizationService.getAllGroups());
    }
}
