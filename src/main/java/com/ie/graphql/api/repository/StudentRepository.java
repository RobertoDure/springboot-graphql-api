package com.ie.graphql.api.repository;

import com.ie.graphql.api.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // find student by email
    Student findByEmail(String email);
}