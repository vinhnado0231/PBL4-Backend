package com.example.backend.repository;

import com.example.backend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface IGroupRepository extends JpaRepository<Group, Long> {
}
