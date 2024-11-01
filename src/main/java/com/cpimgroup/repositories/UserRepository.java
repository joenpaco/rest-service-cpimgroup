package com.cpimgroup.repositories;

import com.cpimgroup.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, String> {
  Optional<Usuario> findByEmail(String email);
}
