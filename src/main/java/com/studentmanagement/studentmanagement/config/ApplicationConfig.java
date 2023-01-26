package com.studentmanagement.studentmanagement.config;


import com.studentmanagement.studentmanagement.Dto.Authority;
import com.studentmanagement.studentmanagement.Entity.Student;
import com.studentmanagement.studentmanagement.Entity.Teacher;
import com.studentmanagement.studentmanagement.Repository.StudentRepository;
import com.studentmanagement.studentmanagement.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<Student> student = studentRepository.findByUsername(username);
                Optional<Teacher> teacher = teacherRepository.findByUsername(username);
                if (student.isPresent() && student.get().getAuthority().equals(Authority.STUDENT.toString())) {
                    return student.get();
                } else if (teacher.isPresent() && teacher.get().getAuthority().equals(Authority.TEACHER.toString())) {
                    return teacher.get();
                } else {
                    throw new UsernameNotFoundException("UserName Not found");
                }
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}