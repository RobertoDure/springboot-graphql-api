package com.ie.graphql.api.controller;

import com.ie.graphql.api.model.Student;
import com.ie.graphql.api.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@GraphQlTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockitoBean
    private StudentService studentService;

    @Test
    @DisplayName("Get All Students - Should Return List of Students")
    void getAllStudents_ShouldReturnListOfStudents() {
        // Arrange
        List<Student> students = Arrays.asList(
                createTestStudent(1L, "John Doe"),
                createTestStudent(2L, "Jane Smith")
        );

        when(studentService.getAllStudents()).thenReturn(students);

        // Act & Assert
        graphQlTester.document("query { getAllStudents { id name email } }")
                .execute()
                .path("data.getAllStudents")
                .entityList(Student.class)
                .hasSize(2)
                .satisfies(studentList -> {
                    assert studentList.get(0).getName().equals("John Doe");
                    assert studentList.get(1).getName().equals("Jane Smith");
                });
    }

    @Test
    @DisplayName("Get Student by ID - Should Return Student")
    void getStudent_ShouldReturnStudent_WhenIdExists() {
        // Arrange
        Student student = createTestStudent(1L, "John Doe");
        when(studentService.getStudentById(1L)).thenReturn(student);

        // Act & Assert
        graphQlTester.document("query { getStudent(id: \"1\") { id name email } }")
                .execute()
                .path("data.getStudent")
                .entity(Student.class)
                .satisfies(s -> {
                    assert s.getId().equals(1L);
                    assert s.getName().equals("John Doe");
                });
    }

    @Test
    @DisplayName("Create Student - Should Return Created Student")
    void createStudent_ShouldReturnCreatedStudent() {
        // Arrange
        Student student = createTestStudent(1L, "New Student");

        when(studentService.createStudent(
                anyString(), anyString(), anyString(), anyString(), anyInt(),
                anyString(), anyString(), anyLong(), anyString(), anyString()
        )).thenReturn(student);

        // Act & Assert
        String createMutation = """
            mutation {
                createStudent(
                    name: "New Student",
                    email: "new@example.com",
                    address: "123 New St",
                    dateOfBirth: "1998-01-15",
                    idClassroom: 1,
                    gender: "Male",
                    nationality: "American",
                    idTeacher: "1",
                    guardianName: "Parent Name",
                    guardianContact: "123-456-7890"
                ) {
                    id
                    name
                    email
                }
            }
        """;

        graphQlTester.document(createMutation)
                .execute()
                .path("data.createStudent")
                .entity(Student.class)
                .satisfies(s -> {
                    assert s.getId().equals(1L);
                    assert s.getName().equals("New Student");
                });
    }

    private Student createTestStudent(Long id, String name) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dob = sdf.parse("1995-05-15");

            return Student.builder()
                    .id(id)
                    .name(name)
                    .email(name.toLowerCase().replace(" ", ".") + "@example.com")
                    .address("123 Test St")
                    .dateOfBirth(dob)
                    .idClassroom(1)
                    .gender("Male")
                    .nationality("American")
                    .idTeacher(1L)
                    .guardianName("Parent Name")
                    .guardianContact("123-456-7890")
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException("Error creating test student", e);
        }
    }
}
