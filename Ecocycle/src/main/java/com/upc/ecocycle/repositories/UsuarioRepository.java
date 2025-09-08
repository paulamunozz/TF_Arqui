package com.upc.ecocycle.repositories;

import com.upc.ecocycle.enitites.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByCodigo(String codigoUsuario);

    boolean existsByCodigo(String codigo);
}
