package com.horrorcore.car_show.services;

import com.horrorcore.car_show.dtos.UserRequest;
import com.horrorcore.car_show.entities.UserCredential;
import com.horrorcore.car_show.repositories.UserCredentialRepository;
import com.horrorcore.car_show.services.testutils.TestDataFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserCredentialServiceTest {

    @Mock
    private UserCredentialRepository userCredentialRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserCredentialService userService;

    @Test
    void shouldCreateUser_encodePasswordAndSave_thenReturnSuccessMessage() {
        UserRequest req = TestDataFactory.createUserRequest("a@b.com", "plain");

        when(passwordEncoder.encode("plain")).thenReturn("encoded");
        when(userCredentialRepository.save(any(UserCredential.class))).thenAnswer(i -> i.getArgument(0));

        String result = userService.createUser(req);

        assertEquals("User has been created", result);

        ArgumentCaptor<UserCredential> captor = ArgumentCaptor.forClass(UserCredential.class);
        verify(userCredentialRepository, times(1)).save(captor.capture());

        UserCredential saved = captor.getValue();
        assertEquals("a@b.com", saved.getEmail());
        assertEquals("encoded", saved.getPassword());
    }

    @Test
    void shouldPropagateException_whenRepositorySaveFails() {
        UserRequest req = TestDataFactory.createUserRequest("x@y.com", "pw");
        when(passwordEncoder.encode("pw")).thenReturn("enc");
        when(userCredentialRepository.save(any(UserCredential.class))).thenThrow(new RuntimeException("DB"));

        assertThrows(RuntimeException.class, () -> userService.createUser(req));
    }
}


