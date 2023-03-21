package com.goorm.okim.infra.repository;

import com.goorm.okim.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.*;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
