package com.upc.ecocycle.repositories;

import com.upc.ecocycle.enitites.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCodigo(String codigoUsuario);
    boolean existsByCodigo(String codigo);
}
