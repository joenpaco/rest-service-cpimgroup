package com.cpimgroup.services;

import com.cpimgroup.errors.UsuarioEmailException;
import com.cpimgroup.errors.UsuarioNotFoundException;
import com.cpimgroup.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.cpimgroup.models.Usuario;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  public static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
  public static final String EL_CORREO_YA_REGISTRADO = "El correo ya registrado";
  public static final String SECRET = "estaeslaclavesecretaparaencriptarqueserequiereparalaseguridaddelaaplicacion";
  public static final boolean ACTIVE = true;
  private final UserRepository userRepository;

  @Value("${jwt.token.secret}")
  private String secret = "";

  @Value("${validation.active.message}")
  private boolean active;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Usuario registerUser(Usuario usuario) {
    if (userRepository.findByEmail(usuario.getEmail()).isPresent()) {
      throw new UsuarioEmailException(EL_CORREO_YA_REGISTRADO);
    }

    String token =
        Jwts.builder()
            .setSubject(usuario.getEmail())
            .setIssuedAt(new Date())
            .signWith(getSigningKey())
            .compact();

    usuario.setToken(token);
    usuario.setCreated(new Date());
    usuario.setLastLogin(usuario.getCreated());
    usuario.setModified(usuario.getCreated());
    usuario.setActive(ACTIVE);

    return userRepository.save(usuario);
  }

  @Override
  public List<Usuario> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public Usuario updateUser(String userId, Usuario updatedUser) {
    Usuario existingUser =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UsuarioNotFoundException(USUARIO_NO_ENCONTRADO));

    existingUser.setName(updatedUser.getName());
    existingUser.setEmail(updatedUser.getEmail());
    existingUser.setPassword(updatedUser.getPassword());
    existingUser.setPhones(updatedUser.getPhones());
    existingUser.setModified(new Date());
    existingUser.setLastLogin(existingUser.getModified());

    return userRepository.save(existingUser);
  }

  @Override
  public void deleteUser(String userId) {
    Usuario user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UsuarioNotFoundException(USUARIO_NO_ENCONTRADO));

    userRepository.delete(user);
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
