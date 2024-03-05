package com.example.repository;

import com.example.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(path = "courses")
public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByOrderByNameDesc();
    List<Course> findAllByOrderByName();
}
