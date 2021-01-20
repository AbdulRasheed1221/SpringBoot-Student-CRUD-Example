package com.rest.Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.Application.model.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

}
