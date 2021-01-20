package com.rest.Application.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.Application.exception.ResourceNotFoundException;
import com.rest.Application.model.Student;
import com.rest.Application.repository.StudentRepository;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	private StudentRepository repository;

	// get all students
	// end point : http://localhost:8080/api/v1/student
	@GetMapping("/students")
	public List<Student> getAllStudents() {
		return repository.findAll();
	}

	// get student by Id
	// http://localhost:8080/api/v1/students/1
	@GetMapping("/student/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Integer studentid)
			throws ResourceNotFoundException {
		Student student = repository.findById(studentid)
				.orElseThrow(() -> new ResourceNotFoundException("student not found :: " + studentid));
		logger.debug("retrive student information " +student.getId());
		return ResponseEntity.ok().body(student);
	}

	// create student
	// http://localhost:8080/api/v1/student
	@PostMapping("/student")
	public Student createStudent(@Valid @RequestBody Student student) {
		return repository.save(student);
	}

	// updating student details based on id
	// http://localhost:8080/api/v1/student/1
	@PutMapping("/student/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable(value = "id") Integer studentid,
			@Valid @RequestBody Student studentdetails) throws ResourceNotFoundException {
		Student student = repository.findById(studentid)
				.orElseThrow(() -> new ResourceNotFoundException("student not found :: " + studentid));
		student.setFirstName(studentdetails.getFirstName());
		student.setLastName(studentdetails.getLastName());
		student.setEmail(studentdetails.getEmail());
		student.setDept(studentdetails.getDept());

		final Student updatedStudent = repository.save(student);
		return ResponseEntity.ok(updatedStudent);
	}

	// delete the student based on id
	// endpoint: http://localhost:8080/api/v1/student/2
	@DeleteMapping("/student/{id}")
	public Map<String, Boolean> deleteStudent(@PathVariable(value = "id") Integer studentId)
			throws ResourceNotFoundException {
		Student student = repository.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("student not found :: " + studentId));

		repository.delete(student);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
