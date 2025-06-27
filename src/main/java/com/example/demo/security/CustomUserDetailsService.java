package com.example.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.CustomUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomUserRepository customUserRepository;

    @Override
    public UserDetails loadUserByUsername(String employeeNoStr) throws UsernameNotFoundException {
        try {
            int employeeNo = Integer.parseInt(employeeNoStr);
            User user = customUserRepository.findByEmployeeNo(employeeNo)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with employee_no: " + employeeNo));
            return new CustomUserDetails(user);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid employee number format: " + employeeNoStr);
        }
    }
}