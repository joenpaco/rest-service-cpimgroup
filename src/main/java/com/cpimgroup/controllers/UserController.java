package com.cpimgroup.controllers;

import com.cpimgroup.models.Usuario;
import com.cpimgroup.services.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios resource")
public class UserController {

  public static final String MENSAJE = "mensaje";
  private final UserServiceImpl userService;

  public UserController(UserServiceImpl userService) {
    this.userService = userService;
  }

  @Operation(summary = "Registra a un Usuario en la base de datos")
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody @Valid Usuario usuario) {
    try {
      Usuario savedUser = userService.registerUser(usuario);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(Collections.singletonMap(MENSAJE, e.getMessage()));
    }
  }

  @Operation(summary = "Se obtienen todos los Usuarios registrados en la base de datos")
  @GetMapping
  public ResponseEntity<List<Usuario>> getAllUsers() {
    List<Usuario> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @Operation(summary = "Actualiza los datos de un Usuario en la base de datos")
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid Usuario user) {
    try {
      Usuario updatedUser = userService.updateUser(id, user);
      return ResponseEntity.ok(updatedUser);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Collections.singletonMap(MENSAJE, e.getMessage()));
    }
  }

  @Operation(summary = "Elimina a un Usuario en la base de datos")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable String id) {
    try {
      userService.deleteUser(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Collections.singletonMap(MENSAJE, e.getMessage()));
    }
  }
}
