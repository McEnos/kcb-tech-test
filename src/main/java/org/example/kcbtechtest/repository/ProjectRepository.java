package org.example.kcbtechtest.repository;

import org.example.kcbtechtest.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
