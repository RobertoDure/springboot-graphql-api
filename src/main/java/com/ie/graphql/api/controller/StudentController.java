package com.ie.graphql.api.controller;

import com.ie.graphql.api.model.Student;
import com.ie.graphql.api.service.StudentService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import java.util.Date;
import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @QueryMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @QueryMapping
    public Student getStudent(@Argument Long id) {
        return studentService.getStudentById(id);
    }

    @MutationMapping
    public Student createStudent(
            @Argument String name,
            @Argument String email,
            @Argument String address,
            @Argument String dateOfBirth,
            @Argument int idClassroom,
            @Argument String gender,
            @Argument String nationality,
            @Argument Long idTeacher,
            @Argument String guardianName,
            @Argument String guardianContact) {

        return  studentService.createStudent(name, email, address, dateOfBirth, idClassroom,
                gender, nationality, idTeacher, guardianName, guardianContact);

    }

    @MutationMapping
    public Student updateStudent(
            @Argument Long id,
            @Argument String name,
            @Argument String email,
            @Argument String address,
            @Argument Date dateOfBirth,
            @Argument Integer idClassroom,
            @Argument String gender,
            @Argument String nationality,
            @Argument Long idTeacher,
            @Argument String guardianName,
            @Argument String guardianContact) {
        return studentService.updateStudent(id, name, email, address, dateOfBirth, idClassroom,
                gender, nationality, idTeacher, guardianName, guardianContact);
    }

    @MutationMapping
    public Boolean deleteStudent(@Argument Long id) {
        return studentService.deleteStudent(id);
    }
}