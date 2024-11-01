package com.cpimgroup.services;

import com.cpimgroup.models.Usuario;

import java.util.List;

public interface UserService {
  Usuario registerUser(Usuario usuario);

  List<Usuario> getAllUsers();

  Usuario updateUser(String userId, Usuario updatedUser);

  void deleteUser(String userId);
}
