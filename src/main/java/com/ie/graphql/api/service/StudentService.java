package com.ie.graphql.api.service;

import com.ie.graphql.api.exception.ConflictException;
import com.ie.graphql.api.exception.ResourceNotFoundException;
import com.ie.graphql.api.exception.ServiceException;
import com.ie.graphql.api.exception.ValidationException;
import com.ie.graphql.api.model.Student;
import com.ie.graphql.api.repository.StudentRepository;
import com.ie.graphql.api.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;



@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Gets all students from the database.
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Gets a student by their ID.
     *
     * @param id the ID of the student
     * @return the student with the specified ID
     * @throws ResourceNotFoundException if student with given id is not found
     */
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", id));
    }

    /**
     * Creates a new student with the provided details.
     *
     * @param name            the new name of the student
     * @param email           the new email of the student
     * @param address         the new address of the student
     * @param dateOfBirth     the new date of birth of the student
     * @param idClassroom     the new classroom ID of the student
     * @param gender          the gender of the student
     * @param nationality     the nationality of the student
     * @param idTeacher       the ID of the teacher associated with the student
     * @param guardianName    the name of the guardian
     * @param guardianContact the contact information of the guardian
     * @return the created student
     * @throws ValidationException if required fields are missing or invalid
     * @throws ConflictException   if a student with the same email already exists
     * @throws ServiceException    if there's an error while saving the student
     */
    public Student createStudent(String name, String email, String address,
                                 String dateOfBirth, int idClassroom, String gender,
                                 String nationality, Long idTeacher, String guardianName,
                                 String guardianContact) {

        // Validate required fields
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("name", "Student name cannot be empty");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("email", "Student email cannot be empty");
        }

        if (getStudentByEmail(email) != null) {
            throw new ConflictException("Student", email, "email already registered to another student");
        }

        try {
            Student student = Student.builder()
                    .name(name)
                    .email(email)
                    .address(address)
                    .dateOfBirth(DateUtils.parseDate(dateOfBirth))
                    .idClassroom(idClassroom)
                    .gender(gender)
                    .nationality(nationality)
                    .idTeacher(idTeacher)
                    .guardianName(guardianName)
                    .guardianContact(guardianContact)
                    .build();

            return studentRepository.save(student);
        } catch (Exception e) {
            throw new ServiceException("Failed to create student: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing student with the provided details.
     *
     * @param id              the ID of the student to update
     * @param name            the new name of the student
     * @param email           the new email of the student
     * @param address         the new address of the student
     * @param dateOfBirth     the new date of birth of the student
     * @param idClassroom     the new classroom ID of the student
     * @param gender          the new gender of the student
     * @param nationality     the new nationality of the student
     * @param idTeacher       the new ID of the teacher associated with the student
     * @param guardianName    the new name of the guardian
     * @param guardianContact the new contact information of the guardian
     * @return the updated student
     * @throws ResourceNotFoundException if student with given id is not found
     * @throws ValidationException       if provided data is invalid
     * @throws ConflictException         if email is already in use by another student
     * @throws ServiceException          if there's an error while updating the student
     */
    public Student updateStudent(Long id, String name, String email, String address,
                                 Date dateOfBirth, Integer idClassroom, String gender,
                                 String nationality, Long idTeacher, String guardianName,
                                 String guardianContact) {

        // getStudentById will throw ResourceNotFoundException if student doesn't exist
        Student existingStudent = getStudentById(id);

        // Validate email if it's being updated
        if (email != null && !email.equals(existingStudent.getEmail())) {
            if (email.trim().isEmpty()) {
                throw new ValidationException("email", "Student email cannot be empty");
            }

            Student studentEmailValidation = getStudentByEmail(email);
            if (studentEmailValidation.getEmail() != null && !studentEmailValidation.getId().equals(id)) {
                throw new ConflictException("Student", email, "email already registered to another student");
            }

            // Validate name if it's being updated
            if (name != null && name.trim().isEmpty()) {
                throw new ValidationException("name", "Student name cannot be empty");
            }

            try {
                if (name != null) {
                    existingStudent.setName(name);
                }
                if (address != null) {
                    existingStudent.setAddress(address);
                }
                if (dateOfBirth != null) {
                    existingStudent.setDateOfBirth(dateOfBirth);
                }
                if (idClassroom != null) {
                    existingStudent.setIdClassroom(idClassroom);
                }
                if (gender != null) {
                    existingStudent.setGender(gender);
                }
                if (nationality != null) {
                    existingStudent.setNationality(nationality);
                }
                if (idTeacher != null) {
                    existingStudent.setIdTeacher(idTeacher);
                }
                if (guardianName != null) {
                    existingStudent.setGuardianName(guardianName);
                }
                if (guardianContact != null) {
                    existingStudent.setGuardianContact(guardianContact);
                }

                return studentRepository.save(existingStudent);
            } catch (Exception e) {
                throw new ServiceException("Failed to update student: " + e.getMessage(), e);
            }
        }
     return null;
    }
    /**
     * Deletes a student by their ID.
     *
     * @param id the ID of the student to delete
     * @throws ResourceNotFoundException if student with given id is not found
     * @throws ServiceException if there's an error while deleting the student
     */
    public boolean deleteStudent (Long id){
        // Check if student exists
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student", id);
        }

        try {
            studentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new ServiceException("Failed to delete student: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a student by their email.
     *
     * @param email the email of the student
     * @return the student with the specified email
     * @throws ValidationException if the email is empty or null
     * @throws ResourceNotFoundException if no student with the given email is found
     */
    public Student getStudentByEmail(String email) {

        return studentRepository.findByEmail(email);
    }
}