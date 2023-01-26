package com.studentmanagement.studentmanagement.Service;

import com.studentmanagement.studentmanagement.Dto.AuthResponse;
import com.studentmanagement.studentmanagement.Dto.Authority;
import com.studentmanagement.studentmanagement.Dto.UserDTO;
import com.studentmanagement.studentmanagement.Entity.Student;
import com.studentmanagement.studentmanagement.Entity.Teacher;
import com.studentmanagement.studentmanagement.Repository.StudentRepository;
import com.studentmanagement.studentmanagement.Repository.TeacherRepository;
import com.studentmanagement.studentmanagement.config.JwtService;
import com.studentmanagement.studentmanagement.exception.UserNameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponse userRegister(UserDTO userDTO) {
        Optional<Student> student = studentRepository.findByUsername(userDTO.getUsername());
        Optional<Teacher> teacher = teacherRepository.findByUsername(userDTO.getUsername());
        if (student.isPresent() || teacher.isPresent()) {
            throw new UserNameAlreadyExistException("UserName already exist");
        } else {
            userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

            if (userDTO.getAuthority().equals(Authority.STUDENT)) {
                Student saveStudent = Student.builder()
                        .email(userDTO.getEmail())
                        .password(userDTO.getPassword())
                        .username(userDTO.getUsername())
                        .authority(userDTO.getAuthority().name())
                        .build();

                studentRepository.save(saveStudent);

                String token = jwtService.generateToken(saveStudent);
                return AuthResponse.builder().token(token).build();

            } else if (userDTO.getAuthority().equals(Authority.TEACHER)) {
                Teacher saveTeacher = Teacher.builder()
                        .email(userDTO.getEmail())
                        .username(userDTO.getUsername())
                        .password(userDTO.getPassword())
                        .authority(userDTO.getAuthority().name())
                        .build();

                teacherRepository.save(saveTeacher);

                String token = jwtService.generateToken(saveTeacher);
                return AuthResponse.builder().token(token).build();
            } else {
                throw new UserNameAlreadyExistException("");
            }

        }
    }

    public AuthResponse userLogin(String username, String password) {
        Optional<Student> student = studentRepository.findByUsername(username);
        Optional<Teacher> teacher = teacherRepository.findByUsername(username);

        if (student.isPresent() && bCryptPasswordEncoder.matches(password, student.get().getPassword())) {
            String token = jwtService.generateToken(student.get());
            return AuthResponse.builder().token(token).build();
        } else if (teacher.isPresent() && bCryptPasswordEncoder.matches(password, teacher.get().getPassword())) {
            String token = jwtService.generateToken(teacher.get());
            return AuthResponse.builder().token(token).build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
