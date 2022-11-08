package com.example.backend.repository;

import com.example.backend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupRepository extends JpaRepository<Group, Long> {
}
