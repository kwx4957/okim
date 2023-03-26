package com.goorm.okim.presentation.organazation;

import com.goorm.okim.common.Response;
import com.goorm.okim.infra.repository.OrganizationRepository;
import com.goorm.okim.service.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class OrganizationController {

    private final OrganizationRepository organizationRepository;
    private final TaskService taskService;

    public OrganizationController(OrganizationRepository organizationRepository, TaskService taskService) {
        this.organizationRepository = organizationRepository;
        this.taskService = taskService;
    }

    @GetMapping("/groupname")
    public ResponseEntity<?> getListGroupName(){
        return Response.success(organizationRepository.findAll());
    }


    @GetMapping("/group/{groupId}/tasks")
    public ResponseEntity<?> getGroupTasks(@PathVariable long groupId, Pageable pageable) {
        return Response.success(taskService.getGroupTasks(groupId, pageable));
    }
}
