package com.horrorcore.car_show.services;

import com.horrorcore.car_show.dtos.UserRequest;
import com.horrorcore.car_show.entities.UserCredential;
import com.horrorcore.car_show.repositories.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCredentialService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    public String createUser(UserRequest userRequest) {
        UserCredential userCredential = UserCredential.builder()
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .role("USER")
                .build();
        userCredentialRepository.save(userCredential);

        return "User has been created";
    }
}
