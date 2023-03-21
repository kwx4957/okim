package com.goorm.okim.presentation.domain.organazation;

import com.goorm.okim.common.Response;
import com.goorm.okim.infra.repository.OrganizationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class OrganizationController {

    private final OrganizationRepository organizationRepository;

    public OrganizationController(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @GetMapping("/groupname")
    public ResponseEntity<?> getListGroupName(){
        return Response.success(organizationRepository.findAll());
    }
}
