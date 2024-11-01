package com.cpimgroup.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.cpimgroup.errors.UsuarioEmailException;
import com.cpimgroup.errors.UsuarioNotFoundException;
import com.cpimgroup.models.Phone;
import com.cpimgroup.models.Usuario;
import com.cpimgroup.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Value("${jwt.token.secret}")
    private String secret = "mysecretkey";

    @Value("${validation.active.message}")
    private boolean active = true;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        Usuario newUser = new Usuario();
        newUser.setEmail("test@example.com");
        newUser.setName("Test User");
        newUser.setPassword("Password123!");
        newUser.setPhones(List.of(new Phone()));

        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario registeredUser = userService.registerUser(newUser);

        assertNotNull(registeredUser.getToken());
        assertEquals(active, registeredUser.isActive());
        assertNotNull(registeredUser.getCreated());
        assertEquals(registeredUser.getCreated(), registeredUser.getLastLogin());
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        Usuario existingUser = new Usuario();
        existingUser.setEmail("existing@example.com");

        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        UsuarioEmailException exception = assertThrows(
                UsuarioEmailException.class,
                () -> userService.registerUser(existingUser)
        );

        assertEquals("El correo ya registrado", exception.getMessage());
        verify(userRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testGetAllUsers() {
        List<Usuario> userList = List.of(
                new Usuario(),
                new Usuario()
        );

        when(userRepository.findAll()).thenReturn(userList);

        List<Usuario> users = userService.getAllUsers();

        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser_Success() {
        String userId = UUID.randomUUID().toString();
        Usuario existingUser = new Usuario();
        existingUser.setId(userId);
        existingUser.setEmail("oldemail@example.com");
        existingUser.setName("Old Name");

        Usuario updatedUser = new Usuario();
        updatedUser.setEmail("newemail@example.com");
        updatedUser.setName("New Name");
        updatedUser.setPassword("NewPassword123!");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultUser = userService.updateUser(userId, updatedUser);

        assertEquals("newemail@example.com", resultUser.getEmail());
        assertEquals("New Name", resultUser.getName());
        assertEquals("NewPassword123!", resultUser.getPassword());
        assertNotNull(resultUser.getModified());
        assertEquals(resultUser.getModified(), resultUser.getLastLogin());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        String userId = UUID.randomUUID().toString();
        Usuario updatedUser = new Usuario();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UsuarioNotFoundException exception = assertThrows(
                UsuarioNotFoundException.class,
                () -> userService.updateUser(userId, updatedUser)
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository, never()).save(any(Usuario.class));
    }

    @Test
    void testDeleteUser_Success() {
        String userId = UUID.randomUUID().toString();
        Usuario existingUser = new Usuario();
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        doNothing().when(userRepository).delete(existingUser);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        String userId = UUID.randomUUID().toString();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UsuarioNotFoundException exception = assertThrows(
                UsuarioNotFoundException.class,
                () -> userService.deleteUser(userId)
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository, never()).delete(any(Usuario.class));
    }
}

